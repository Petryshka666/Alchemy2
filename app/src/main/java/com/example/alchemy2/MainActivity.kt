package com.example.alchemy2

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Rect
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.alchemy2.data.Element
import com.example.alchemy2.data.GameLogic
import com.example.alchemy2.data.CombinationData
import kotlin.math.abs
import android.media.MediaPlayer
import android.widget.Toast
import org.json.JSONArray


class MainActivity : AppCompatActivity() {
    private lateinit var soundAdd: MediaPlayer //музыка добавления элементов на экран
    private lateinit var soundDelete: MediaPlayer//музыка удаления
    private lateinit var soundCombine: MediaPlayer//музыка комбинации

    private lateinit var gameLogic: GameLogic
    private lateinit var elementsContainer: FrameLayout//контейнер элементов

    private var dx = 0f
    private var dy = 0f

    private lateinit var deleteZone: ImageView // зона удаления(дырка справа снизу)

    private val PREFS_NAME = "AlchemyPrefs"//файл
    private val ELEMENTS_KEY = "saved_elements"

    override fun onResume() {
        super.onResume()
        App.resumeMusic()//возобновлю фоновую музыку
        restoreElementsState()
    }

    override fun onPause() {
        super.onPause()
        App.pauseMusic()
        saveElementsState()
    }

    private fun saveElementsState() {
        val elements = gameLogic._elementsOnScreen.map { element ->
            mapOf(//ключ-значение
                "id" to element.id,
                "offsetX" to element.offsetX,
                "offsetY" to element.offsetY
            )
        }

        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)//получение объекта для хранения простых данных в формате ключ-значение между сессиями приложения
        val json = JSONArray(elements).toString() //преобразовать список эл-тов в json массив(строку) т к несколько св-в у эл-та
        prefs.edit().putString(ELEMENTS_KEY, json).apply()
    }

    private fun restoreElementsState() {
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)//получение объекта
        val json = prefs.getString(ELEMENTS_KEY, null) ?: return//получу строку

        try {
            val elementsArray = JSONArray(json)//получу json-массив

            val elementsToRestore = mutableListOf<Element>()

            for (i in 0 until elementsArray.length()) {
                val elementObj = elementsArray.getJSONObject(i)
                val id = elementObj.getInt("id")
                val offsetX = elementObj.getDouble("offsetX").toFloat()
                val offsetY = elementObj.getDouble("offsetY").toFloat()

                //создаю эл-т по айди и координатам
                val element = gameLogic.createElementById(id, offsetX, offsetY) ?: continue
                elementsToRestore.add(element)
            }

            removeAllLinearLayouts()
            gameLogic.clearElements()
            gameLogic._elementsOnScreen.addAll(elementsToRestore)

            for (element in elementsToRestore) {
                addElementToScreen(element)//добавляю эл-ты на экран
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //класс для определения двойного касания на эл-те экрана
    private class DoubleTapDetector(private val onDoubleTap: () -> Unit) {
        private var lastTapTime: Long = 0
        private val doubleTapTimeout = 300 // время между нажатиями в мс

        fun onTap(): Boolean {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastTapTime < doubleTapTimeout) {
                onDoubleTap()
                return true
            }
            lastTapTime = currentTime//если это первое касание - запомнили когда было
            return false
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT //запрет поворота экрана

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        gameLogic = GameLogic(CombinationData.combinations)//список комбинаций в параметры


        elementsContainer = findViewById(R.id.elements_container)
        val showBaseElementsButton: Button = findViewById(R.id.show_base_elements_button)

        val pointer=findViewById<ImageView>(R.id.previous)
        pointer.setOnClickListener{
            val intent = Intent(this, MainMenuActivity::class.java)//создается объект Intent, который говорит Android, что нужно перейти к новой активности (MainActivity)
            startActivity(intent)
            finish()//закрываю
        }

       deleteZone = findViewById(R.id.hole)

        deleteZone.setOnClickListener {
            deleteAllElements()
            Toast.makeText(this, "Вы очистили все элементы", Toast.LENGTH_SHORT).show()
        }

        //обработка нажатия на кнопку
        showBaseElementsButton.setOnClickListener {
            showBaseElements()
        }


        soundAdd = MediaPlayer.create(this, R.raw.add_sound)
        soundDelete = MediaPlayer.create(this, R.raw.delete)
        soundCombine = MediaPlayer.create(this, R.raw.combine)



    }
    private fun showBaseElements() {
        val displayMetrics = resources.displayMetrics

        val screenWidth = displayMetrics.widthPixels
        val screenHeight = displayMetrics.heightPixels

        val imageWidth = 100
        val imageHeight = 100

        removeAllLinearLayouts()

        gameLogic.addBaseElements(screenWidth, screenHeight, imageWidth, imageHeight)

        //получаю состояние звуков
        if(App.areSoundEnabled()) { soundAdd.start() }


        val elements = gameLogic._elementsOnScreen

        for (element in elements) {
            val elementLayout = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                layoutParams = FrameLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,//ширина зависит от содержимого
                    LinearLayout.LayoutParams.WRAP_CONTENT//высота зависит от содержимого
                ).apply {
                    //позиция контейнера
                    leftMargin = element.offsetX.toInt()
                    topMargin = element.offsetY.toInt()
                }

                //добавляем детектор двойного нажатия
                val doubleTapDetector = DoubleTapDetector {
                    duplicateElement(element)
                }

                setOnTouchListener { view, event ->//ОБРАБОТЧИК КАСАНИЯ LINEARLAYOUT, VIEW(на чем), EVENT(ИНФА)
                    when (event.action) {//ТИП
                        MotionEvent.ACTION_DOWN -> {//КОСНУЛСЯ
                            if (doubleTapDetector.onTap()) {
                                return@setOnTouchListener true
                            }
                            dx = event.rawX - view.x
                            dy = event.rawY - view.y
                        }
                        MotionEvent.ACTION_MOVE -> {
                            view.animate()//анимация смены положения
                                //новые позиции
                                .x(event.rawX - dx)
                                .y(event.rawY - dy)
                                .setDuration(0)
                                .start()
                        }
                        MotionEvent.ACTION_UP -> {//отпустил палец
                            gameLogic.updateElementPosition(element, view.x, view.y)
                            checkForCollisionsWithDeleteZone(view, element)
                            checkForCollisions(view, element)
                        }
                        else -> return@setOnTouchListener false
                    }
                    true
                }
            }

            val imageView = ImageView(this).apply {

                setImageResource(element.imageRes)//получу ресурс изображения эл-та
                layoutParams = LinearLayout.LayoutParams(150, 150)//параметры изображения(будет зависеть размер эл-тов на экране)
                tag = element.id//tag чтоб по изображению эл-т н-ти
            }
            elementLayout.addView(imageView)

            val textView = TextView(this).apply {
                text = element.name
                textSize = 11f
                setTextColor(resources.getColor(android.R.color.black))
                gravity = android.view.Gravity.CENTER
            }
            elementLayout.addView(textView)

            elementsContainer.addView(elementLayout)//добавлю на экран
        }
    }

    private fun duplicateElement(originalElement: Element) {//параметр -дублируемый эл-т
        val displayMetrics = resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        val screenHeight = displayMetrics.heightPixels
        val imageWidth = 100
        val imageHeight = 100

        //создаем новый элемент со случайными координатами
        val newElement = gameLogic.createElementById(
            originalElement.id,
            gameLogic.getRandomX(screenWidth, imageWidth),
            gameLogic.getRandomY(screenHeight, imageHeight)
        ) ?: return

        //добавить новый элемент в логику игры
        gameLogic._elementsOnScreen.add(newElement)

        //добавить новый элемент на экран
        addElementToScreen(newElement)

        //звук
        if(App.areSoundEnabled()) { soundAdd.start() }
    }

    private fun checkForCollisions(movingView: View, movingElement: Element) { //view - представление на экране
        val movingRect = Rect()//создала прямоугольник
        movingView.getHitRect(movingRect)//связала с элементом

        for (otherElement in gameLogic._elementsOnScreen) {
            //пропускаем сам элемент
            if (otherElement == movingElement) {continue }

            val otherView = findViewByElement(otherElement) ?: continue//нашла другой виджет
            val otherRect = Rect()
            otherView.getHitRect(otherRect)

            if (Rect.intersects(movingRect, otherRect)) {
                combineAndRemoveElements(movingElement, otherElement, movingView, otherView)
                break
            }
        }

    }

    private fun findViewByElement(element: Element): View? {//ищу представление элемента
        for (i in 0 until elementsContainer.childCount) {
            val child = elementsContainer.getChildAt(i)
            if (child is LinearLayout) {
                val imageView = child.getChildAt(0)//в лин контейнере у картинки индекс 0
                val viewTag = imageView.tag

                //проверяем, есть ли дубликаты элементов с таким же id
                val duplicateElements = gameLogic._elementsOnScreen.count { it.id == element.id }

                if (viewTag == element.id) {//тег виджета совпал с айди элемента
                    if (duplicateElements > 1) {
                        //если есть дубликаты, проверяем координаты
                        val layoutParams = child.layoutParams as? FrameLayout.LayoutParams ?: continue//параметры относительно родителя
                        if (abs(layoutParams.leftMargin - element.offsetX.toInt()) < 10 &&
                            abs(layoutParams.topMargin - element.offsetY.toInt()) < 10) {
                            return child//ЗНАЧИТ ЭТО ТОТ ЧТО ИСКАЛА ЧТОБ СГРУППИРОВАТЬ НЕ С ЭЛЕМЕНТОМ НА ПРОТИВОПОЛОЖНОЙФ СТОРОНЕ
                        }
                    } else {

                        return child
                    }
                }
            }
        }
        return null
    }

    private fun combineAndRemoveElements(element1: Element, element2: Element, view1: View, view2: View) {
        //средняя позицию между двумя элементами
        val combinedX = (element1.offsetX + element2.offsetX) / 2
        val combinedY = (element1.offsetY + element2.offsetY) / 2

        // новый элемент в средней позиции
        val newElement = gameLogic.combineElements(element1, element2)
            ?: gameLogic.combineElements(element2, element1)
            ?: return

        //обновить позицию нового элемента
        newElement.offsetX = combinedX
        newElement.offsetY = combinedY

        elementsContainer.removeView(view1)//удалить с экрана представления
        elementsContainer.removeView(view2)

        gameLogic._elementsOnScreen.remove(element1)//удалить эл-ты
        gameLogic._elementsOnScreen.remove(element2)

        //добавить новый элемент на экран
        addElementToScreen(newElement)

        if(App.areSoundEnabled()) { soundCombine.start() }
    }

    private fun addElementToScreen(element: Element) {
        val elementLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = FrameLayout.LayoutParams(
                150,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                leftMargin = element.offsetX.toInt()
                topMargin = element.offsetY.toInt()
            }
        }

        val imageView = ImageView(this).apply {
            setImageResource(element.imageRes)
            layoutParams = LinearLayout.LayoutParams(150, 150)
            tag = element.id
        }
        elementLayout.addView(imageView)

        val textView = TextView(this).apply {
            text = element.name
            textSize = 11f
            setTextColor(resources.getColor(android.R.color.black))
            gravity = android.view.Gravity.CENTER
        }
        elementLayout.addView(textView)

        elementsContainer.addView(elementLayout)

        //+ детектор двойного нажатия
        val doubleTapDetector = DoubleTapDetector {
            duplicateElement(element)
        }

        elementLayout.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (doubleTapDetector.onTap()) {
                        return@setOnTouchListener true
                    }
                    dx = event.rawX - view.x
                    dy = event.rawY - view.y
                }
                MotionEvent.ACTION_MOVE -> {
                    view.animate()
                        .x(event.rawX - dx)
                        .y(event.rawY - dy)
                        .setDuration(0)
                        .start()
                }
                MotionEvent.ACTION_UP -> {
                    gameLogic.updateElementPosition(element, view.x, view.y)
                    checkForCollisionsWithDeleteZone(view, element)
                    checkForCollisions(view, element)
                }
                else -> return@setOnTouchListener false
            }
            true
        }
    }
    //метод удаления всех элементов
    private fun deleteAllElements() {
        removeAllLinearLayouts()
        if(App.areSoundEnabled()) {soundDelete.start()}
        //очищаем список элементов в GameLogic
        gameLogic.clearElements()
    }

    private fun checkForCollisionsWithDeleteZone(view: View, element: Element) {

        val deleteZoneRect = Rect(deleteZone.left,  deleteZone.top, deleteZone.right, deleteZone.bottom)

        val elementRect = Rect()//прямоугольник из эл-та
        view.getHitRect(elementRect)


        if (Rect.intersects(deleteZoneRect, elementRect)) {

            elementsContainer.removeView(view)
            gameLogic._elementsOnScreen.remove(element)
            if(App.areSoundEnabled()) { soundDelete.start()}
        }
    }
    private fun removeAllLinearLayouts() {//так как в framelayout находятся не только linearlayout(картинки эти), но и кнопки, чтоб их не удалять
        for (i in elementsContainer.childCount - 1 downTo 0) {
            val child = elementsContainer.getChildAt(i)
            if (child is LinearLayout) {
                elementsContainer.removeView(child)
            }
        }
    }

}
