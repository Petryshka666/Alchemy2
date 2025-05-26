package com.example.alchemy2.data

import com.example.alchemy2.R

class GameLogic(private val combinationData: List<Combination>) {

    var _elementsOnScreen = mutableListOf<Element>()//список хранит все эл-ты


    //добавление базовых элементов
    fun addBaseElements(screenWidth: Int, screenHeight: Int, imageWidth: Int, imageHeight: Int) {
        val baseElements = listOf(
            Element(1, "Вода", R.drawable.water, getRandomX(screenWidth, imageWidth), getRandomY(screenHeight, imageHeight)),
            Element(2, "Земля", R.drawable.earth, getRandomX(screenWidth, imageWidth), getRandomY(screenHeight, imageHeight)),
            Element(3, "Огонь", R.drawable.ogon, getRandomX(screenWidth, imageWidth), getRandomY(screenHeight, imageHeight)),
            Element(4, "Воздух", R.drawable.vozdux, getRandomX(screenWidth, imageWidth), getRandomY(screenHeight, imageHeight))
        )
        _elementsOnScreen.addAll(baseElements)
    }

    //генерация случайной координаты X с учетом отступов
    fun getRandomX(screenWidth: Int, imageWidth: Int): Float {
        val min_otstup = 90
        return (min_otstup until (screenWidth - imageWidth - min_otstup)).random().toFloat()
    }

    fun getRandomY(screenHeight: Int, imageHeight: Int): Float {
        val otstup_verx = 100
        val otstup_vniz = 300

        val maxY = screenHeight - imageHeight - otstup_vniz//считаю макс y

        return if (maxY > otstup_verx) {
            (otstup_verx until maxY).random().toFloat()
        } else {
            otstup_verx.toFloat()
        }
    }

    //комбинирование элементов
    fun combineElements(element1: Element, element2: Element): Element? {
        //поиск подходящей комбинации
        val combination = combinationData.find {
            (it.element1Id == element1.id && it.element2Id == element2.id) ||
                    (it.element1Id == element2.id && it.element2Id == element1.id)
        } ?: return null

        //создание нового элемента из полученного combination
        val newElement = when (combination.resultId) {
            5 -> Element(5, "Болото", R.drawable.boloto, element1.offsetX, element1.offsetY) // Вода + Земля = Болото
            6 -> Element(6, "Энергия", R.drawable.energy, element1.offsetX, element1.offsetY) // Огонь + Воздух = Энергия
            7 -> Element(7, "Лава", R.drawable.lava, element1.offsetX, element1.offsetY) // Земля + Огонь = Лава
            8 -> Element(8, "Пар", R.drawable.par, element1.offsetX, element1.offsetY) // Вода + Огонь = Пар
            9 -> Element(9, "Давление", R.drawable.davleniye, element1.offsetX, element1.offsetY) // Земля + Земля = Давление
            10 -> Element(10, "Пыль", R.drawable.pyl, element1.offsetX, element1.offsetY) // Воздух + Земля = Пыль
            11 -> Element(11, "Жизнь", R.drawable.life, element1.offsetX, element1.offsetY) // Болото + Энергия = Жизнь
            12 -> Element(12, "Камень", R.drawable.kamen, element1.offsetX, element1.offsetY) // Лава + Вода = Камень
            13 -> Element(13, "Озеро", R.drawable.ozero, element1.offsetX, element1.offsetY) // Вода + Вода = Озеро
            14 -> Element(14, "Песок", R.drawable.pesok, element1.offsetX, element1.offsetY) // Камень + Воздух = Песок
            15 -> Element(15, "Глина", R.drawable.glina, element1.offsetX, element1.offsetY) // Песок + Болото = Глина
            16 -> Element(16, "Буря", R.drawable.burya, element1.offsetX, element1.offsetY) // Воздух + Энергия = Буря
            17 -> Element(17, "Ветер", R.drawable.veter, element1.offsetX, element1.offsetY) // Воздух + Давление = Ветер
            18 -> Element(18, "Грязь", R.drawable.gruaz, element1.offsetX, element1.offsetY) // Болото + Земля = Грязь
            19 -> Element(19, "Пустыня", R.drawable.pustinya, element1.offsetX, element1.offsetY) // Песок + Воздух = Пустыня
            20 -> Element(20, "Вулкан", R.drawable.volcano, element1.offsetX, element1.offsetY) // Лава + Горы = Вулкан
            21 -> Element(21, "Облако", R.drawable.oblaco, element1.offsetX, element1.offsetY) // Воздух + Вода = Облако
            22 -> Element(22, "Холод", R.drawable.holod, element1.offsetX, element1.offsetY) // Воздух + Энергия = Холод
            23 -> Element(23, "Лед", R.drawable.ice, element1.offsetX, element1.offsetY) // Вода + Холод = Лед
            24 -> Element(24, "Небо", R.drawable.nebo, element1.offsetX, element1.offsetY) // Воздух + Энергия = Небо
            25 -> Element(25, "Гейзер", R.drawable.geyzer, element1.offsetX, element1.offsetY) // Вода + Давление = Гейзер
            26 -> Element(26, "Спирт", R.drawable.spirt, element1.offsetX, element1.offsetY) // Вода + Энергия = Спирт
            27 -> Element(27, "Водка", R.drawable.vodka, element1.offsetX, element1.offsetY) // Спирт + Вода = Водка
            28 -> Element(28, "Металл", R.drawable.metal, element1.offsetX, element1.offsetY) // Лава + Давление = Металл
            29 -> Element(29, "Звук", R.drawable.sound, element1.offsetX, element1.offsetY) // Воздух + Энергия = Звук
            30 -> Element(30, "Гром", R.drawable.grom, element1.offsetX, element1.offsetY) // Звук + Электричество = Гром
            31 -> Element(31, "Кремний", R.drawable.si, element1.offsetX, element1.offsetY) // Песок + Огонь = Кремний
            32 -> Element(32, "Пепел", R.drawable.pepel, element1.offsetX, element1.offsetY) // Лава + Воздух = Пепел
            33 -> Element(33, "Кирпич", R.drawable.kirpich, element1.offsetX, element1.offsetY) // Глина + Огонь = Кирпич
            34 -> Element(34, "Стекло", R.drawable.steklo, element1.offsetX, element1.offsetY) // Кремний + Огонь = Стекло
            35 -> Element(35, "Песочные часы", R.drawable.pesochclock, element1.offsetX, element1.offsetY) // Песок + Время = Песочные часы
            36 -> Element(36, "Время", R.drawable.time, element1.offsetX, element1.offsetY) // Давление + Энергия = Время
            37 -> Element(37, "Электричество", R.drawable.electricity, element1.offsetX, element1.offsetY) // Энергия + Энергия = Электричество
            38 -> Element(38, "Водород", R.drawable.vodorod, element1.offsetX, element1.offsetY) // Вода + Энергия = Водород
            39 -> Element(39, "Магнит", R.drawable.magnit, element1.offsetX, element1.offsetY) // Металл + Электричество = Магнит
            40 -> Element(40, "Бактерия", R.drawable.bacterium, element1.offsetX, element1.offsetY) // Жизнь + Грязь = Бактерия
            41 -> Element(41, "Сера", R.drawable.sera, element1.offsetX, element1.offsetY) // Лава + Пепел = Сера
            42 -> Element(42, "Кислота", R.drawable.kislota, element1.offsetX, element1.offsetY) // Сера + Вода = Кислота
            43 -> Element(43, "Соль", R.drawable.salt, element1.offsetX, element1.offsetY) // Вода + Камень = Соль
            44 -> Element(44, "Море", R.drawable.sea, element1.offsetX, element1.offsetY) // Озеро + Вода = Море
            45 -> Element(45, "Океан", R.drawable.ocean, element1.offsetX, element1.offsetY) // Море + Вода = Океан
            46 -> Element(46, "Соленая вода", R.drawable.saltwater, element1.offsetX, element1.offsetY) // Море + Соль = Соленая вода
            47 -> Element(47, "Водоросли", R.drawable.vodorosli, element1.offsetX, element1.offsetY) // Море + Жизнь = Водоросли
            48 -> Element(48, "Йод", R.drawable.iod, element1.offsetX, element1.offsetY) // Водоросли + Соль = Йод
            49 -> Element(49, "Планктон", R.drawable.plancton, element1.offsetX, element1.offsetY) // Водоросли + Жизнь = Планктон
            50 -> Element(50, "Ракушка", R.drawable.rakishka, element1.offsetX, element1.offsetY) // Планктон + Кальций = Ракушка
            51 -> Element(51, "Жемчуг", R.drawable.gemchug, element1.offsetX, element1.offsetY) // Ракушка + Время = Жемчуг
            52 -> Element(52, "Известняк", R.drawable.izvestnyak, element1.offsetX, element1.offsetY) // Ракушки + Время = Известняк
            53 -> Element(53, "Цемент", R.drawable.cement, element1.offsetX, element1.offsetY) // Известняк + Вода = Цемент
            54 -> Element(54, "Бетон", R.drawable.beton, element1.offsetX, element1.offsetY) // Цемент + Вода = Бетон
            55 -> Element(55, "Грипп", R.drawable.gripp, element1.offsetX, element1.offsetY) // Человек + Вирус = Грипп
            56 -> Element(56, "Медуза", R.drawable.jellyfish, element1.offsetX, element1.offsetY) // Жизнь + Море = Медуза
            57 -> Element(57, "Звезда", R.drawable.starmorskaya, element1.offsetX, element1.offsetY) // Небо + Энергия = Звезда
            58 -> Element(58, "Моллюски", R.drawable.mollusk, element1.offsetX, element1.offsetY) // Ракушки + Жизнь = Моллюски
            59 -> Element(59, "Слизни", R.drawable.slizen, element1.offsetX, element1.offsetY) // Моллюски + Грязь = Слизни
            60 -> Element(60, "Мидии", R.drawable.midii, element1.offsetX, element1.offsetY) // Моллюски + Море = Мидии
            61 -> Element(61, "Головоногие", R.drawable.golovonogiye, element1.offsetX, element1.offsetY) // Моллюски + Эволюция = Головоногие
            62 -> Element(62, "Осьминог", R.drawable.osminog, element1.offsetX, element1.offsetY) // Головоногие + Море = Осьминог
            63 -> Element(63, "Клетки", R.drawable.kletki, element1.offsetX, element1.offsetY) // Жизнь + Разделение = Клетки
            64 -> Element(64, "Членистоногие", R.drawable.chlenistonogiye, element1.offsetX, element1.offsetY) // Жизнь + Эволюция = Членистоногие
            65 -> Element(65, "Паук", R.drawable.spider, element1.offsetX, element1.offsetY) // Членистоногие + Жизнь = Паук
            66 -> Element(66, "Рыба", R.drawable.fish, element1.offsetX, element1.offsetY) // Жизнь + Вода = Рыба
            67 -> Element(67, "Икра", R.drawable.caviar, element1.offsetX, element1.offsetY) // Рыба + Яйцо = Икра
            68 -> Element(68, "Червь", R.drawable.worm, element1.offsetX, element1.offsetY) // Жизнь + Грязь = Червь
            69 -> Element(69, "Бабочка", R.drawable.butterfly, element1.offsetX, element1.offsetY) // Членистоногие + Эволюция = Бабочка
            70 -> Element(70, "Жук", R.drawable.bug, element1.offsetX, element1.offsetY) // Членистоногие + Жизнь = Жук
            71 -> Element(71, "Насекомые", R.drawable.nasekomye, element1.offsetX, element1.offsetY) // Жук + Бабочка = Насекомые
            72 -> Element(72, "Муравьи", R.drawable.muravey, element1.offsetX, element1.offsetY) // Насекомые + Колония = Муравьи
            73 -> Element(73, "Стрекоза", R.drawable.strekoza, element1.offsetX, element1.offsetY) // Насекомые + Вода = Стрекоза
            74 -> Element(74, "Скорпион", R.drawable.scorpio, element1.offsetX, element1.offsetY) // Насекомые + Ядовитость = Скорпион
            75 -> Element(75, "Змея", R.drawable.snake, element1.offsetX, element1.offsetY) // Жизнь + Эволюция = Змея
            76 -> Element(76, "Яйцо", R.drawable.egg, element1.offsetX, element1.offsetY) // Жизнь + Размножение = Яйцо
            77 -> Element(77, "Птица", R.drawable.bird, element1.offsetX, element1.offsetY) // Яйцо + Эволюция = Птица
            78 -> Element(78, "Ящерица", R.drawable.yasheritsa, element1.offsetX, element1.offsetY) // Змея + Эволюция = Ящерица
            79 -> Element(79, "Черепаха", R.drawable.cherepaha, element1.offsetX, element1.offsetY) // Ящерица + Панцирь = Черепаха
            80 -> Element(80, "Динозавр", R.drawable.dinozavr, element1.offsetX, element1.offsetY) // Ящерица + Размер = Динозавр
            81 -> Element(81, "Зверь", R.drawable.zver, element1.offsetX, element1.offsetY) // Жизнь + Эволюция = Зверь
            82 -> Element(82, "Кит", R.drawable.kit, element1.offsetX, element1.offsetY) // Зверь + Море = Кит
            83 -> Element(83, "Дракон", R.drawable.dragon, element1.offsetX, element1.offsetY) // Динозавр + Летать = Дракон
            84 -> Element(84, "Феникс", R.drawable.feniks, element1.offsetX, element1.offsetY) // Птица + Огонь = Феникс
            85 -> Element(85, "Лягушка", R.drawable.frog, element1.offsetX, element1.offsetY) // Земля + Вода = Лягушка
            86 -> Element(86, "Кенгуру", R.drawable.kenguru, element1.offsetX, element1.offsetY) // Зверь + Прыжок = Кенгуру
            87 -> Element(87, "Курица", R.drawable.kuritsa, element1.offsetX, element1.offsetY) // Птица + Яйцо = Курица
            88 -> Element(88, "Яичница", R.drawable.omlet, element1.offsetX, element1.offsetY) // Курица + Огонь = Яичница
            89 -> Element(89, "Верблюд", R.drawable.verblud, element1.offsetX, element1.offsetY) // Зверь + Пустыня = Верблюд
            90 -> Element(90, "Человек", R.drawable.people, element1.offsetX, element1.offsetY) // Жизнь + Интеллект = Человек
            91 -> Element(91, "Керамика", R.drawable.keramika, element1.offsetX, element1.offsetY) // Глина + Огонь = Керамика
            92 -> Element(92, "Домашний скот", R.drawable.domskot, element1.offsetX, element1.offsetY) // Животное + Человек = Домашний скот
            93 -> Element(93, "Молоко", R.drawable.milk, element1.offsetX, element1.offsetY) // Корова + Вымя = Молоко
            94 -> Element(94, "Кефир", R.drawable.kefir, element1.offsetX, element1.offsetY) // Молоко + Бактерии = Кефир
            95 -> Element(95, "Свинья", R.drawable.pig, element1.offsetX, element1.offsetY) // Домашний скот + Грязь = Свинья
            96 -> Element(96, "Стейк", R.drawable.steik, element1.offsetX, element1.offsetY) // Свинья + Огонь = Стейк
            97 -> Element(97, "Жир", R.drawable.lip, element1.offsetX, element1.offsetY) // Свинья + Еда = Жир
            98 -> Element(98, "Любовь", R.drawable.love, element1.offsetX, element1.offsetY) // Человек + Человек = Любовь
            99 -> Element(99, "Алкоголик", R.drawable.alcoholic, element1.offsetX, element1.offsetY) // Человек + Водка = Алкоголик
            100 -> Element(100, "Больной", R.drawable.bolnoy, element1.offsetX, element1.offsetY) // Человек + Вирус = Больной
            101 -> Element(101, "Мох", R.drawable.mox, element1.offsetX, element1.offsetY) // Жизнь + Влажность = Мох
            102 -> Element(102, "Гриб", R.drawable.mushroom, element1.offsetX, element1.offsetY) // Жизнь + Темнота = Гриб
            103 -> Element(103, "Лишайник", R.drawable.lishainik, element1.offsetX, element1.offsetY) // Мох + Гриб = Лишайник
            104 -> Element(104, "Плесень", R.drawable.plesen, element1.offsetX, element1.offsetY) // Гриб + Грязь = Плесень
            105 -> Element(105, "Шаман", R.drawable.shaman, element1.offsetX, element1.offsetY) // Человек + Гриб = Шаман
            106 -> Element(106, "Папоротник", R.drawable.paporotnik, element1.offsetX, element1.offsetY) // Болото + Мох = Папоротник
            107 -> Element(107, "Трава", R.drawable.trava, element1.offsetX, element1.offsetY) // Земля + Мох = Трава
            108 -> Element(108, "Навоз", R.drawable.navoz, element1.offsetX, element1.offsetY) // Трава + Домашний скот = Навоз
            109 -> Element(109, "Селитра", R.drawable.selitra, element1.offsetX, element1.offsetY) // Известняк + Навоз = Селитра
            110 -> Element(110, "Порох", R.drawable.poroh, element1.offsetX, element1.offsetY) // Сера + Селитра = Порох
            111 -> Element(111, "Семена", R.drawable.semena, element1.offsetX, element1.offsetY) // Жизнь + Земля = Семена
            112 -> Element(112, "Дерево", R.drawable.derevo, element1.offsetX, element1.offsetY) // Земля + Семена = Дерево
            113 -> Element(113, "Роща", R.drawable.rosha, element1.offsetX, element1.offsetY) // Дерево + Дерево = Роща
            114 -> Element(114, "Лес", R.drawable.les, element1.offsetX, element1.offsetY) // Роща + Роща = Лес
            115 -> Element(115, "Уголь", R.drawable.ugol, element1.offsetX, element1.offsetY) // Огонь + Дерево = Уголь
            116 -> Element(116, "Чугун", R.drawable.chugun, element1.offsetX, element1.offsetY) // Металл + Уголь = Чугун
            117 -> Element(117, "Алмаз", R.drawable.almaz, element1.offsetX, element1.offsetY) // Уголь + Давление = Алмаз
            118 -> Element(118, "Медведь", R.drawable.medved, element1.offsetX, element1.offsetY) // Зверь + Лес = Медведь
            119 -> Element(119, "Тростник", R.drawable.trostnik, element1.offsetX, element1.offsetY) // Трава + Болото = Тростник
            120 -> Element(120, "Табак", R.drawable.tabak, element1.offsetX, element1.offsetY) // Огонь + Трава = Табак
            121 -> Element(121, "Дым", R.drawable.dym, element1.offsetX, element1.offsetY) // Огонь + Табак = Дым
            122 -> Element(122, "Торф", R.drawable.torf, element1.offsetX, element1.offsetY) // Болото + Дерево = Торф
            123 -> Element(123, "Нефть", R.drawable.neft, element1.offsetX, element1.offsetY) // Торф + Давление = Нефть
            124 -> Element(124, "Бензин", R.drawable.benzin, element1.offsetX, element1.offsetY) // Давление + Нефть = Бензин
            125 -> Element(125, "Взрыв", R.drawable.vzryv, element1.offsetX, element1.offsetY) // Бензин + Огонь = Взрыв
            126 -> Element(126, "Кактус", R.drawable.kaktus, element1.offsetX, element1.offsetY) // Пустыня + Дерево = Кактус
            127 -> Element(127, "Кошениль", R.drawable.koshenil, element1.offsetX, element1.offsetY) // Кактус + Жук = Кошениль
            128 -> Element(128, "Кармин", R.drawable.karmin, element1.offsetX, element1.offsetY) // Кошениль + Огонь = Кармин
            129 -> Element(129, "Цветок", R.drawable.cvetok, element1.offsetX, element1.offsetY) // Вода + Семена = Цветок
            130 -> Element(130, "Пчела", R.drawable.pchela, element1.offsetX, element1.offsetY) // Цветок + Жук = Пчела
            131 -> Element(131, "Фрукт", R.drawable.frukt, element1.offsetX, element1.offsetY) // Цветок + Дерево = Фрукт
            132 -> Element(132, "Сок", R.drawable.sok, element1.offsetX, element1.offsetY) // Фрукт + Давление = Сок
            133 -> Element(133, "Коктейль", R.drawable.kokteyl, element1.offsetX, element1.offsetY) // Водка + Сок = Коктейль
            134 -> Element(134, "Банан", R.drawable.banan, element1.offsetX, element1.offsetY) // Трава + Фрукт = Банан
            135 -> Element(135, "Бамбук", R.drawable.bambuk, element1.offsetX, element1.offsetY) // Земля + Трава = Бамбук
            136 -> Element(136, "Лиана", R.drawable.liana, element1.offsetX, element1.offsetY) // Трава + Дерево = Лиана
            137 -> Element(137, "Пальма", R.drawable.palma, element1.offsetX, element1.offsetY) // Песок + Папоротник = Пальма
            138 -> Element(138, "Куст", R.drawable.kust, element1.offsetX, element1.offsetY) // Земля + Папоротник = Куст
            139 -> Element(139, "Ягода", R.drawable.yagoda, element1.offsetX, element1.offsetY) // Куст + Фрукт = Ягода
            140 -> Element(140, "Ликер", R.drawable.liker, element1.offsetX, element1.offsetY) // Спирт + Ягода = Ликер
            141 -> Element(141, "Камыш", R.drawable.kamysh, element1.offsetX, element1.offsetY) // Вода + Трава = Камыш
            142 -> Element(142, "Паровой котёл", R.drawable.parovoy_kotel, element1.offsetX, element1.offsetY) // Пар + Металл = Паровой котёл
            143 -> Element(143, "Субмарина", R.drawable.submarina, element1.offsetX, element1.offsetY) // Металл + Кит = Субмарина
            144 -> Element(144, "Паровой двигатель", R.drawable.parovoy_dvigatel, element1.offsetX, element1.offsetY) // Паровой котёл + Уголь = Паровой двигатель
            145 -> Element(145, "Трактор", R.drawable.traktor, element1.offsetX, element1.offsetY) // Паровой двигатель + Земля = Трактор
            146 -> Element(146, "Самолёт", R.drawable.samolet, element1.offsetX, element1.offsetY) // Птица + Металл = Самолет
            147 -> Element(147, "Алюминий", R.drawable.alyuminiy, element1.offsetX, element1.offsetY) // Металл + Самолет = Алюминий
            148 -> Element(148, "Пилот", R.drawable.pilot, element1.offsetX, element1.offsetY) // Самолет + Человек = Пилот
            149 -> Element(149, "Мотор", R.drawable.motor, element1.offsetX, element1.offsetY) // Паровой двигатель + Бензин = Мотор
            150 -> Element(150, "Гидроплан", R.drawable.gidroplan, element1.offsetX, element1.offsetY) // Самолет + Вода = Гидроплан
            151 -> Element(151, "Инструмент", R.drawable.instrument, element1.offsetX, element1.offsetY) // Металл + Человек = Инструмент
            152 -> Element(152, "Яд", R.drawable.yad, element1.offsetX, element1.offsetY) // Скорпион + Инструмент = Яд
            153 -> Element(153, "Бриллиант", R.drawable.brilliant, element1.offsetX, element1.offsetY) // Алмаз + Инструмент = Бриллиант
            154 -> Element(154, "Бумага", R.drawable.bumaga, element1.offsetX, element1.offsetY) // Тростник + Инструмент = Бумага
            155 -> Element(155, "Шерсть", R.drawable.sherst, element1.offsetX, element1.offsetY) // Инструмент + Домашний скот = Шерсть
            156 -> Element(156, "Древесина", R.drawable.drevesina, element1.offsetX, element1.offsetY) // Инструмент + Дерево = Древесина
            157 -> Element(157, "Углерод", R.drawable.uglerod, element1.offsetX, element1.offsetY) // Огонь + Древесина = Углерод
            158 -> Element(158, "Ткань", R.drawable.tkan, element1.offsetX, element1.offsetY) // Инструмент + Шерсть = Ткань
            159 -> Element(159, "Лекарство", R.drawable.lekarstvo, element1.offsetX, element1.offsetY) // Шаман + Яд = Лекарство
            160 -> Element(160, "Термиты", R.drawable.termity, element1.offsetX, element1.offsetY) // Древесина + Муравьи = Термиты
            161 -> Element(161, "Обезьяна", R.drawable.obezyana, element1.offsetX, element1.offsetY) // Человек + Шерсть = Обезьяна
            162 -> Element(162, "Труп", R.drawable.trup, element1.offsetX, element1.offsetY) // Человек + Яд = Труп
            163 -> Element(163, "Зомби", R.drawable.zombi, element1.offsetX, element1.offsetY) // Жизнь + Труп = Зомби
            164 -> Element(164, "Мумия", R.drawable.mumiya, element1.offsetX, element1.offsetY) // Ткань + Труп = Мумия
            165 -> Element(165, "Виноград", R.drawable.vinograd, element1.offsetX, element1.offsetY) // Земля + Древесина = Виноград
            166 -> Element(166, "Вино", R.drawable.vino, element1.offsetX, element1.offsetY) // Бактерия + Виноград = Вино
            167 -> Element(167, "Лодка", R.drawable.lodka, element1.offsetX, element1.offsetY) // Вода + Древесина = Лодка
            168 -> Element(168, "Деревянный корабль", R.drawable.derevyaniy_korabl, element1.offsetX, element1.offsetY) // Древесина + Лодка = Деревянный корабль
            169 -> Element(169, "Парусник", R.drawable.parusnik, element1.offsetX, element1.offsetY) // Ткань + Деревянный корабль = Парусник
            170 -> Element(170, "Парусная лодка", R.drawable.parusnaya_lodka, element1.offsetX, element1.offsetY) // Лодка + Ткань = Парусная лодка
            171 -> Element(171, "Пароход", R.drawable.paroxod, element1.offsetX, element1.offsetY) // Паровой двигатель + Деревянный корабль = Пароход
            172 -> Element(172, "Катер", R.drawable.kater, element1.offsetX, element1.offsetY) // Лодка + Мотор = Катер
            173 -> Element(173, "Газонокосилка", R.drawable.gazonokosilka, element1.offsetX, element1.offsetY) // Трава + Инструмент = Газонокосилка
            174 -> Element(174, "Хижина", R.drawable.hizhina, element1.offsetX, element1.offsetY) // Человек + Камень = Хижина
            175 -> Element(175, "Кирпичный дом", R.drawable.kirpichniy_dom, element1.offsetX, element1.offsetY) // Кирпич + Бетон = Кирпичный дом
            176 -> Element(176, "Небоскрёб", R.drawable.neboskreb, element1.offsetX, element1.offsetY) // Кирпичный дом + Стекло = Небоскреб
            177 -> Element(177, "Город", R.drawable.gorod, element1.offsetX, element1.offsetY) // Небоскреб + Небоскреб = Город
            178 -> Element(178, "Оружие", R.drawable.oruzhie, element1.offsetX, element1.offsetY) // Металл + Инструмент = Оружие
            179 -> Element(179, "Охотник", R.drawable.ohotnik, element1.offsetX, element1.offsetY) // Человек + Оружие = Охотник
            180 -> Element(180, "Перо", R.drawable.pero, element1.offsetX, element1.offsetY) // Птица + Охотник = Перо
            181 -> Element(181, "Воин", R.drawable.voin, element1.offsetX, element1.offsetY) // Оружие + Охотник = Воин
            182 -> Element(182, "Кровь", R.drawable.krov, element1.offsetX, element1.offsetY) // Зверь + Воин = Кровь
            183 -> Element(183, "Герой", R.drawable.geroy, element1.offsetX, element1.offsetY) // Дракон + Воин = Герой
            184 -> Element(184, "Колумб", R.drawable.kolumb, element1.offsetX, element1.offsetY) // Парусник + Герой = Колумб
            185 -> Element(185, "Вампир", R.drawable.vampir, element1.offsetX, element1.offsetY) // Кровь + Человек = Вампир
            186 -> Element(186, "Робин Гуд", R.drawable.robin_gud, element1.offsetX, element1.offsetY) // Лес + Герой = Робин Гуд
            187 -> Element(187, "Пожарный", R.drawable.pozharniy, element1.offsetX, element1.offsetY) // Воин + Огонь = Пожарный
            188 -> Element(188, "Рыбак", R.drawable.rybak, element1.offsetX, element1.offsetY) // Охотник + Рыба = Рыбак
            189 -> Element(189, "Книга", R.drawable.kniga, element1.offsetX, element1.offsetY) // Перо + Бумага = Книга
            190 -> Element(190, "Пашня", R.drawable.pashnya, element1.offsetX, element1.offsetY) // Земля + Трактор = Пашня
            191 -> Element(191, "Лён", R.drawable.len, element1.offsetX, element1.offsetY) // Цветок + Пашня = Лён
            192 -> Element(192, "Отравленное оружие", R.drawable.otravlennoe_oruzhie, element1.offsetX, element1.offsetY) // Оружие + Яд = Отравленное оружие
            193 -> Element(193, "Колесо", R.drawable.koleso, element1.offsetX, element1.offsetY) // Инструмент + Древесина = Колесо
            194 -> Element(194, "Телега", R.drawable.telega, element1.offsetX, element1.offsetY) // Колесо + Древесина = Телега
            195 -> Element(195, "Упряжка", R.drawable.upryazhka, element1.offsetX, element1.offsetY) // Зверь + Телега = Упряжка
            196 -> Element(196, "Паровоз", R.drawable.parovoz, element1.offsetX, element1.offsetY) // Телега + Паровой двигатель = Паровоз
            197 -> Element(197, "Велосипед", R.drawable.velosiped, element1.offsetX, element1.offsetY) // Колесо + Колесо = Велосипед
            198 -> Element(198, "Колесница", R.drawable.kolesnica, element1.offsetX, element1.offsetY) // Воин + Телега = Колесница
            199 -> Element(199, "Солнце", R.drawable.solnce, element1.offsetX, element1.offsetY) // Небо + Колесница = Солнце
            200 -> Element(200, "Гладиатор", R.drawable.gladiator, element1.offsetX, element1.offsetY) // Воин + Колесница = Гладиатор
            201 -> Element(201, "Подсолнух", R.drawable.podsolnuh, element1.offsetX, element1.offsetY) // Солнце + Цветок = Подсолнух
            202 -> Element(202, "Подсолнечное масло", R.drawable.oil, element1.offsetX, element1.offsetY) // Подсолнух + Давление = Подсолнечное масло
            203 -> Element(203, "Машина", R.drawable.car, element1.offsetX, element1.offsetY) // Телега + Мотор = Машина
            204 -> Element(204, "Мотоцикл", R.drawable.motobike, element1.offsetX, element1.offsetY) // Велосипед + Мотор = Мотоцикл
            205 -> Element(205, "Аквариум", R.drawable.acuarium, element1.offsetX, element1.offsetY) // Стекло + Рыба = Аквариум
            206 -> Element(206, "Бомба", R.drawable.bomba, element1.offsetX, element1.offsetY) // Металл + Порох = Бомба
            207 -> Element(207, "Одежда", R.drawable.clothes, element1.offsetX, element1.offsetY) // Человек + Ткань = Одежда
            208 -> Element(208, "Огнестрельное оружие", R.drawable.weapon, element1.offsetX, element1.offsetY) // Оружие + Порох = Огнестрельное оружие
            209 -> Element(209, "Лампочка", R.drawable.lampa, element1.offsetX, element1.offsetY) // Электричество + Стекло = Лампочка
            210 -> Element(210, "Идея", R.drawable.idea, element1.offsetX, element1.offsetY) // Человек + Лампочка = Идея
            211 -> Element(211, "Свет", R.drawable.svet, element1.offsetX, element1.offsetY) // Электричество + Лампочка = Свет
            212 -> Element(212, "Музыка", R.drawable.music, element1.offsetX, element1.offsetY) // Звук + Идея = Музыка
            213 -> Element(213, "Радуга", R.drawable.raduga, element1.offsetX, element1.offsetY) // Вода + Свет = Радуга
            214 -> Element(214, "Кислород", R.drawable.kislorod, element1.offsetX, element1.offsetY) // Свет + Цветок = Кислород
            215 -> Element(215, "Уксус", R.drawable.uksus, element1.offsetX, element1.offsetY) // Спирт + Кислород = Уксус
            216 -> Element(216, "Гремучий газ", R.drawable.ugarniy_gaz, element1.offsetX, element1.offsetY) // Кислород + Водород = Гремучий газ
            217 -> Element(217, "Озон", R.drawable.ozone, element1.offsetX, element1.offsetY) // Кислород + Электричество = Озон
            218 -> Element(218, "Углекислый газ", R.drawable.uglekisliy_gaz, element1.offsetX, element1.offsetY) // Кислород + Человек = Углекислый газ
            219 -> Element(219, "Газировка", R.drawable.gazirovka, element1.offsetX, element1.offsetY) // Вода + Углекислый газ = Газировка
            220 -> Element(220, "Кока-кола", R.drawable.cola, element1.offsetX, element1.offsetY) // Газировка + Кармин = Кока-кола
            221 -> Element(221, "Новогодняя ёлка", R.drawable.elka, element1.offsetX, element1.offsetY) // Дерево + Лампочка = Новогодняя ёлка
            222 -> Element(222, "Библиотека", R.drawable.library, element1.offsetX, element1.offsetY) // Книга + Книга = Библиотека
            223 -> Element(223, "Учёный", R.drawable.scientist, element1.offsetX, element1.offsetY) // Библиотека + Человек = Учёный
            224 -> Element(224, "Звезда", R.drawable.star, element1.offsetX, element1.offsetY) // Солнце + Учёный = Звезда
            225 -> Element(225, "Космос", R.drawable.space, element1.offsetX, element1.offsetY) // Звезда + Небо = Космос
            226 -> Element(226, "Пенициллин", R.drawable.penicilin, element1.offsetX, element1.offsetY) // Плесень + Учёный = Пенициллин
            227 -> Element(227, "Колибри", R.drawable.calibri, element1.offsetX, element1.offsetY) // Птица + Цветок = Колибри

            else -> null
        }

        if (newElement != null) {
            _elementsOnScreen.add(newElement)
        }
        return newElement
    }

    fun createElementById(id: Int, offsetX: Float, offsetY: Float): Element? {
        return when (id) {
            1 -> Element(1, "Вода", R.drawable.water, offsetX, offsetY)
            2 -> Element(2, "Земля", R.drawable.earth, offsetX, offsetY)
            3 -> Element(3, "Огонь", R.drawable.ogon, offsetX, offsetY)
            4 -> Element(4, "Воздух", R.drawable.vozdux, offsetX, offsetY)
            5 -> Element(5, "Болото", R.drawable.boloto, offsetX, offsetY)
            6 -> Element(6, "Энергия", R.drawable.energy, offsetX, offsetY) // Огонь + Воздух = Энергия
            7 -> Element(7, "Лава", R.drawable.lava, offsetX, offsetY) // Земля + Огонь = Лава
            8 -> Element(8, "Пар", R.drawable.par, offsetX, offsetY) // Вода + Огонь = Пар
            9 -> Element(9, "Давление", R.drawable.davleniye, offsetX, offsetY) // Земля + Земля = Давление
            10 -> Element(10, "Пыль", R.drawable.pyl, offsetX, offsetY) // Воздух + Земля = Пыль
            11 -> Element(11, "Жизнь", R.drawable.life, offsetX, offsetY) // Болото + Энергия = Жизнь
            12 -> Element(12, "Камень", R.drawable.kamen, offsetX, offsetY) // Лава + Вода = Камень
            13 -> Element(13, "Озеро", R.drawable.ozero, offsetX, offsetY) // Вода + Вода = Озеро
            14 -> Element(14, "Песок", R.drawable.pesok, offsetX, offsetY) // Камень + Воздух = Песок
            15 -> Element(15, "Глина", R.drawable.glina, offsetX, offsetY) // Песок + Болото = Глина
            16 -> Element(16, "Буря", R.drawable.burya, offsetX, offsetY) // Воздух + Энергия = Буря
            17 -> Element(17, "Ветер", R.drawable.veter, offsetX, offsetY) // Воздух + Давление = Ветер
            18 -> Element(18, "Грязь", R.drawable.gruaz, offsetX, offsetY) // Болото + Земля = Грязь
            19 -> Element(19, "Пустыня", R.drawable.pustinya, offsetX, offsetY) // Песок + Воздух = Пустыня
            20 -> Element(20, "Вулкан", R.drawable.volcano, offsetX, offsetY) // Лава + Горы = Вулкан
            21 -> Element(21, "Облако", R.drawable.oblaco, offsetX, offsetY) // Воздух + Вода = Облако
            22 -> Element(22, "Холод", R.drawable.holod, offsetX, offsetY) // Воздух + Энергия = Холод
            23 -> Element(23, "Лед", R.drawable.ice, offsetX, offsetY) // Вода + Холод = Лед
            24 -> Element(24, "Небо", R.drawable.nebo, offsetX, offsetY) // Воздух + Энергия = Небо
            25 -> Element(25, "Гейзер", R.drawable.geyzer, offsetX, offsetY) // Вода + Давление = Гейзер
            26 -> Element(26, "Спирт", R.drawable.spirt, offsetX, offsetY) // Вода + Энергия = Спирт
            27 -> Element(27, "Водка", R.drawable.vodka, offsetX, offsetY) // Спирт + Вода = Водка
            28 -> Element(28, "Металл", R.drawable.metal, offsetX, offsetY) // Лава + Давление = Металл
            29 -> Element(29, "Звук", R.drawable.sound, offsetX, offsetY) // Воздух + Энергия = Звук
            30 -> Element(30, "Гром", R.drawable.grom, offsetX, offsetY) // Звук + Электричество = Гром
            31 -> Element(31, "Кремний", R.drawable.si, offsetX, offsetY) // Песок + Огонь = Кремний
            32 -> Element(32, "Пепел", R.drawable.pepel, offsetX, offsetY) // Лава + Воздух = Пепел
            33 -> Element(33, "Кирпич", R.drawable.kirpich, offsetX, offsetY) // Глина + Огонь = Кирпич
            34 -> Element(34, "Стекло", R.drawable.steklo, offsetX, offsetY) // Кремний + Огонь = Стекло
            35 -> Element(35, "Песочные часы", R.drawable.pesochclock, offsetX, offsetY) // Песок + Время = Песочные часы
            36 -> Element(36, "Время", R.drawable.time, offsetX, offsetY) // Давление + Энергия = Время
            37 -> Element(37, "Электричество", R.drawable.electricity, offsetX, offsetY) // Энергия + Энергия = Электричество
            38 -> Element(38, "Водород", R.drawable.vodorod, offsetX, offsetY) // Вода + Энергия = Водород
            39 -> Element(39, "Магнит", R.drawable.magnit, offsetX, offsetY) // Металл + Электричество = Магнит
            40 -> Element(40, "Бактерия", R.drawable.bacterium, offsetX, offsetY) // Жизнь + Грязь = Бактерия
            41 -> Element(41, "Сера", R.drawable.sera, offsetX, offsetY) // Лава + Пепел = Сера
            42 -> Element(42, "Кислота", R.drawable.kislota, offsetX, offsetY) // Сера + Вода = Кислота
            43 -> Element(43, "Соль", R.drawable.salt, offsetX, offsetY) // Вода + Камень = Соль
            44 -> Element(44, "Море", R.drawable.sea, offsetX, offsetY) // Озеро + Вода = Море
            45 -> Element(45, "Океан", R.drawable.ocean, offsetX, offsetY) // Море + Вода = Океан
            46 -> Element(46, "Соленая вода", R.drawable.saltwater, offsetX, offsetY) // Море + Соль = Соленая вода
            47 -> Element(47, "Водоросли", R.drawable.vodorosli, offsetX, offsetY) // Море + Жизнь = Водоросли
            48 -> Element(48, "Йод", R.drawable.iod, offsetX, offsetY) // Водоросли + Соль = Йод
            49 -> Element(49, "Планктон", R.drawable.plancton, offsetX, offsetY) // Водоросли + Жизнь = Планктон
            50 -> Element(50, "Ракушка", R.drawable.rakishka, offsetX, offsetY) // Планктон + Кальций = Ракушка
            51 -> Element(51, "Жемчуг", R.drawable.gemchug, offsetX, offsetY) // Ракушка + Время = Жемчуг
            52 -> Element(52, "Известняк", R.drawable.izvestnyak, offsetX, offsetY) // Ракушки + Время = Известняк
            53 -> Element(53, "Цемент", R.drawable.cement, offsetX, offsetY) // Известняк + Вода = Цемент
            54 -> Element(54, "Бетон", R.drawable.beton, offsetX, offsetY) // Цемент + Вода = Бетон
            55 -> Element(55, "Грипп", R.drawable.gripp, offsetX, offsetY) // Человек + Вирус = Грипп
            56 -> Element(56, "Медуза", R.drawable.jellyfish, offsetX, offsetY) // Жизнь + Море = Медуза
            57 -> Element(57, "Звезда", R.drawable.starmorskaya, offsetX, offsetY) // Небо + Энергия = Звезда
            58 -> Element(58, "Моллюски", R.drawable.mollusk, offsetX, offsetY) // Ракушки + Жизнь = Моллюски
            59 -> Element(59, "Слизни", R.drawable.slizen, offsetX, offsetY) // Моллюски + Грязь = Слизни
            60 -> Element(60, "Мидии", R.drawable.midii, offsetX, offsetY) // Моллюски + Море = Мидии
            61 -> Element(61, "Головоногие", R.drawable.golovonogiye, offsetX, offsetY) // Моллюски + Эволюция = Головоногие
            62 -> Element(62, "Осьминог", R.drawable.osminog, offsetX, offsetY) // Головоногие + Море = Осьминог
            63 -> Element(63, "Клетки", R.drawable.kletki, offsetX, offsetY) // Жизнь + Разделение = Клетки
            64 -> Element(64, "Членистоногие", R.drawable.chlenistonogiye, offsetX, offsetY) // Жизнь + Эволюция = Членистоногие
            65 -> Element(65, "Паук", R.drawable.spider, offsetX, offsetY) // Членистоногие + Жизнь = Паук
            66 -> Element(66, "Рыба", R.drawable.fish, offsetX, offsetY) // Жизнь + Вода = Рыба
            67 -> Element(67, "Икра", R.drawable.caviar, offsetX, offsetY) // Рыба + Яйцо = Икра
            68 -> Element(68, "Червь", R.drawable.worm, offsetX, offsetY) // Жизнь + Грязь = Червь
            69 -> Element(69, "Бабочка", R.drawable.butterfly, offsetX, offsetY) // Членистоногие + Эволюция = Бабочка
            70 -> Element(70, "Жук", R.drawable.bug, offsetX, offsetY) // Членистоногие + Жизнь = Жук
            71 -> Element(71, "Насекомые", R.drawable.nasekomye, offsetX, offsetY) // Жук + Бабочка = Насекомые
            72 -> Element(72, "Муравьи", R.drawable.muravey, offsetX, offsetY) // Насекомые + Колония = Муравьи
            73 -> Element(73, "Стрекоза", R.drawable.strekoza, offsetX, offsetY) // Насекомые + Вода = Стрекоза
            74 -> Element(74, "Скорпион", R.drawable.scorpio, offsetX, offsetY) // Насекомые + Ядовитость = Скорпион
            75 -> Element(75, "Змея", R.drawable.snake, offsetX, offsetY) // Жизнь + Эволюция = Змея
            76 -> Element(76, "Яйцо", R.drawable.egg, offsetX, offsetY) // Жизнь + Размножение = Яйцо
            77 -> Element(77, "Птица", R.drawable.bird, offsetX, offsetY) // Яйцо + Эволюция = Птица
            78 -> Element(78, "Ящерица", R.drawable.yasheritsa, offsetX, offsetY) // Змея + Эволюция = Ящерица
            79 -> Element(79, "Черепаха", R.drawable.cherepaha, offsetX, offsetY) // Ящерица + Панцирь = Черепаха
            80 -> Element(80, "Динозавр", R.drawable.dinozavr, offsetX, offsetY) // Ящерица + Размер = Динозавр
            81 -> Element(81, "Зверь", R.drawable.zver, offsetX, offsetY) // Жизнь + Эволюция = Зверь
            82 -> Element(82, "Кит", R.drawable.kit, offsetX, offsetY) // Зверь + Море = Кит
            83 -> Element(83, "Дракон", R.drawable.dragon, offsetX, offsetY) // Динозавр + Летать = Дракон
            84 -> Element(84, "Феникс", R.drawable.feniks, offsetX, offsetY) // Птица + Огонь = Феникс
            85 -> Element(85, "Лягушка", R.drawable.frog, offsetX, offsetY) // Земля + Вода = Лягушка
            86 -> Element(86, "Кенгуру", R.drawable.kenguru, offsetX, offsetY) // Зверь + Прыжок = Кенгуру
            87 -> Element(87, "Курица", R.drawable.kuritsa, offsetX, offsetY) // Птица + Яйцо = Курица
            88 -> Element(88, "Яичница", R.drawable.omlet, offsetX, offsetY) // Курица + Огонь = Яичница
            89 -> Element(89, "Верблюд", R.drawable.verblud, offsetX, offsetY) // Зверь + Пустыня = Верблюд
            90 -> Element(90, "Человек", R.drawable.people, offsetX, offsetY) // Жизнь + Интеллект = Человек
            91 -> Element(91, "Керамика", R.drawable.keramika, offsetX, offsetY) // Глина + Огонь = Керамика
            92 -> Element(92, "Домашний скот", R.drawable.domskot, offsetX, offsetY) // Животное + Человек = Домашний скот
            93 -> Element(93, "Молоко", R.drawable.milk, offsetX, offsetY) // Корова + Вымя = Молоко
            94 -> Element(94, "Кефир", R.drawable.kefir, offsetX, offsetY) // Молоко + Бактерии = Кефир
            95 -> Element(95, "Свинья", R.drawable.pig, offsetX, offsetY) // Домашний скот + Грязь = Свинья
            96 -> Element(96, "Стейк", R.drawable.steik, offsetX, offsetY) // Свинья + Огонь = Стейк
            97 -> Element(97, "Жир", R.drawable.lip, offsetX, offsetY) // Свинья + Еда = Жир
            98 -> Element(98, "Любовь", R.drawable.love, offsetX, offsetY) // Человек + Человек = Любовь
            99 -> Element(99, "Алкоголик", R.drawable.alcoholic, offsetX, offsetY) // Человек + Водка = Алкоголик
            100 -> Element(100, "Больной", R.drawable.bolnoy, offsetX, offsetY) // Человек + Вирус = Больной
            101 -> Element(101, "Мох", R.drawable.mox, offsetX, offsetY) // Жизнь + Влажность = Мох
            102 -> Element(102, "Гриб", R.drawable.mushroom, offsetX, offsetY) // Жизнь + Темнота = Гриб
            103 -> Element(103, "Лишайник", R.drawable.lishainik, offsetX, offsetY) // Мох + Гриб = Лишайник
            104 -> Element(104, "Плесень", R.drawable.plesen, offsetX, offsetY) // Гриб + Грязь = Плесень
            105 -> Element(105, "Шаман", R.drawable.shaman, offsetX, offsetY) // Человек + Гриб = Шаман
            106 -> Element(106, "Папоротник", R.drawable.paporotnik, offsetX, offsetY) // Болото + Мох = Папоротник
            107 -> Element(107, "Трава", R.drawable.trava, offsetX, offsetY) // Земля + Мох = Трава
            108 -> Element(108, "Навоз", R.drawable.navoz, offsetX, offsetY) // Трава + Домашний скот = Навоз
            109 -> Element(109, "Селитра", R.drawable.selitra, offsetX, offsetY) // Известняк + Навоз = Селитра
            110 -> Element(110, "Порох", R.drawable.poroh, offsetX, offsetY) // Сера + Селитра = Порох
            111 -> Element(111, "Семена", R.drawable.semena, offsetX, offsetY) // Жизнь + Земля = Семена
            112 -> Element(112, "Дерево", R.drawable.derevo, offsetX, offsetY) // Земля + Семена = Дерево
            113 -> Element(113, "Роща", R.drawable.rosha, offsetX, offsetY) // Дерево + Дерево = Роща
            114 -> Element(114, "Лес", R.drawable.les, offsetX, offsetY) // Роща + Роща = Лес
            115 -> Element(115, "Уголь", R.drawable.ugol, offsetX, offsetY) // Огонь + Дерево = Уголь
            116 -> Element(116, "Чугун", R.drawable.chugun, offsetX, offsetY) // Металл + Уголь = Чугун
            117 -> Element(117, "Алмаз", R.drawable.almaz, offsetX, offsetY) // Уголь + Давление = Алмаз
            118 -> Element(118, "Медведь", R.drawable.medved, offsetX, offsetY) // Зверь + Лес = Медведь
            119 -> Element(119, "Тростник", R.drawable.trostnik, offsetX, offsetY) // Трава + Болото = Тростник
            120 -> Element(120, "Табак", R.drawable.tabak, offsetX, offsetY) // Огонь + Трава = Табак
            121 -> Element(121, "Дым", R.drawable.dym, offsetX, offsetY) // Огонь + Табак = Дым
            122 -> Element(122, "Торф", R.drawable.torf, offsetX, offsetY) // Болото + Дерево = Торф
            123 -> Element(123, "Нефть", R.drawable.neft, offsetX, offsetY) // Торф + Давление = Нефть
            124 -> Element(124, "Бензин", R.drawable.benzin, offsetX, offsetY) // Давление + Нефть = Бензин
            125 -> Element(125, "Взрыв", R.drawable.vzryv, offsetX, offsetY) // Бензин + Огонь = Взрыв
            126 -> Element(126, "Кактус", R.drawable.kaktus, offsetX, offsetY) // Пустыня + Дерево = Кактус
            127 -> Element(127, "Кошениль", R.drawable.koshenil, offsetX, offsetY) // Кактус + Жук = Кошениль
            128 -> Element(128, "Кармин", R.drawable.karmin, offsetX, offsetY) // Кошениль + Огонь = Кармин
            129 -> Element(129, "Цветок", R.drawable.cvetok, offsetX, offsetY) // Вода + Семена = Цветок
            130 -> Element(130, "Пчела", R.drawable.pchela, offsetX, offsetY) // Цветок + Жук = Пчела
            131 -> Element(131, "Фрукт", R.drawable.frukt, offsetX, offsetY) // Цветок + Дерево = Фрукт
            132 -> Element(132, "Сок", R.drawable.sok, offsetX, offsetY) // Фрукт + Давление = Сок
            133 -> Element(133, "Коктейль", R.drawable.kokteyl, offsetX, offsetY) // Водка + Сок = Коктейль
            134 -> Element(134, "Банан", R.drawable.banan, offsetX, offsetY) // Трава + Фрукт = Банан
            135 -> Element(135, "Бамбук", R.drawable.bambuk, offsetX, offsetY) // Земля + Трава = Бамбук
            136 -> Element(136, "Лиана", R.drawable.liana, offsetX, offsetY) // Трава + Дерево = Лиана
            137 -> Element(137, "Пальма", R.drawable.palma, offsetX, offsetY) // Песок + Папоротник = Пальма
            138 -> Element(138, "Куст", R.drawable.kust, offsetX, offsetY) // Земля + Папоротник = Куст
            139 -> Element(139, "Ягода", R.drawable.yagoda, offsetX, offsetY) // Куст + Фрукт = Ягода
            140 -> Element(140, "Ликер", R.drawable.liker, offsetX, offsetY) // Спирт + Ягода = Ликер
            141 -> Element(141, "Камыш", R.drawable.kamysh, offsetX, offsetY) // Вода + Трава = Камыш
            142 -> Element(142, "Паровой котёл", R.drawable.parovoy_kotel, offsetX, offsetY) // Пар + Металл = Паровой котёл
            143 -> Element(143, "Субмарина", R.drawable.submarina, offsetX, offsetY) // Металл + Кит = Субмарина
            144 -> Element(144, "Паровой двигатель", R.drawable.parovoy_dvigatel, offsetX, offsetY) // Паровой котёл + Уголь = Паровой двигатель
            145 -> Element(145, "Трактор", R.drawable.traktor, offsetX, offsetY) // Паровой двигатель + Земля = Трактор
            146 -> Element(146, "Самолёт", R.drawable.samolet, offsetX, offsetY) // Птица + Металл = Самолет
            147 -> Element(147, "Алюминий", R.drawable.alyuminiy, offsetX, offsetY) // Металл + Самолет = Алюминий
            148 -> Element(148, "Пилот", R.drawable.pilot, offsetX, offsetY) // Самолет + Человек = Пилот
            149 -> Element(149, "Мотор", R.drawable.motor, offsetX, offsetY) // Паровой двигатель + Бензин = Мотор
            150 -> Element(150, "Гидроплан", R.drawable.gidroplan, offsetX, offsetY) // Самолет + Вода = Гидроплан
            151 -> Element(151, "Инструмент", R.drawable.instrument, offsetX, offsetY) // Металл + Человек = Инструмент
            152 -> Element(152, "Яд", R.drawable.yad, offsetX, offsetY) // Скорпион + Инструмент = Яд
            153 -> Element(153, "Бриллиант", R.drawable.brilliant, offsetX, offsetY) // Алмаз + Инструмент = Бриллиант
            154 -> Element(154, "Бумага", R.drawable.bumaga, offsetX, offsetY) // Тростник + Инструмент = Бумага
            155 -> Element(155, "Шерсть", R.drawable.sherst, offsetX, offsetY) // Инструмент + Домашний скот = Шерсть
            156 -> Element(156, "Древесина", R.drawable.drevesina, offsetX, offsetY) // Инструмент + Дерево = Древесина
            157 -> Element(157, "Углерод", R.drawable.uglerod, offsetX, offsetY) // Огонь + Древесина = Углерод
            158 -> Element(158, "Ткань", R.drawable.tkan, offsetX, offsetY) // Инструмент + Шерсть = Ткань
            159 -> Element(159, "Лекарство", R.drawable.lekarstvo, offsetX, offsetY) // Шаман + Яд = Лекарство
            160 -> Element(160, "Термиты", R.drawable.termity, offsetX, offsetY) // Древесина + Муравьи = Термиты
            161 -> Element(161, "Обезьяна", R.drawable.obezyana, offsetX, offsetY) // Человек + Шерсть = Обезьяна
            162 -> Element(162, "Труп", R.drawable.trup, offsetX, offsetY) // Человек + Яд = Труп
            163 -> Element(163, "Зомби", R.drawable.zombi, offsetX, offsetY) // Жизнь + Труп = Зомби
            164 -> Element(164, "Мумия", R.drawable.mumiya, offsetX, offsetY) // Ткань + Труп = Мумия
            165 -> Element(165, "Виноград", R.drawable.vinograd, offsetX, offsetY) // Земля + Древесина = Виноград
            166 -> Element(166, "Вино", R.drawable.vino, offsetX, offsetY) // Бактерия + Виноград = Вино
            167 -> Element(167, "Лодка", R.drawable.lodka, offsetX, offsetY) // Вода + Древесина = Лодка
            168 -> Element(168, "Деревянный корабль", R.drawable.derevyaniy_korabl, offsetX, offsetY) // Древесина + Лодка = Деревянный корабль
            169 -> Element(169, "Парусник", R.drawable.parusnik, offsetX, offsetY) // Ткань + Деревянный корабль = Парусник
            170 -> Element(170, "Парусная лодка", R.drawable.parusnaya_lodka, offsetX, offsetY) // Лодка + Ткань = Парусная лодка
            171 -> Element(171, "Пароход", R.drawable.paroxod, offsetX, offsetY) // Паровой двигатель + Деревянный корабль = Пароход
            172 -> Element(172, "Катер", R.drawable.kater, offsetX, offsetY) // Лодка + Мотор = Катер
            173 -> Element(173, "Газонокосилка", R.drawable.gazonokosilka, offsetX, offsetY) // Трава + Инструмент = Газонокосилка
            174 -> Element(174, "Хижина", R.drawable.hizhina, offsetX, offsetY) // Человек + Камень = Хижина
            175 -> Element(175, "Кирпичный дом", R.drawable.kirpichniy_dom, offsetX, offsetY) // Кирпич + Бетон = Кирпичный дом
            176 -> Element(176, "Небоскрёб", R.drawable.neboskreb, offsetX, offsetY) // Кирпичный дом + Стекло = Небоскреб
            177 -> Element(177, "Город", R.drawable.gorod, offsetX, offsetY) // Небоскреб + Небоскреб = Город
            178 -> Element(178, "Оружие", R.drawable.oruzhie, offsetX, offsetY) // Металл + Инструмент = Оружие
            179 -> Element(179, "Охотник", R.drawable.ohotnik, offsetX, offsetY) // Человек + Оружие = Охотник
            180 -> Element(180, "Перо", R.drawable.pero, offsetX, offsetY) // Птица + Охотник = Перо
            181 -> Element(181, "Воин", R.drawable.voin, offsetX, offsetY) // Оружие + Охотник = Воин
            182 -> Element(182, "Кровь", R.drawable.krov, offsetX, offsetY) // Зверь + Воин = Кровь
            183 -> Element(183, "Герой", R.drawable.geroy, offsetX, offsetY) // Дракон + Воин = Герой
            184 -> Element(184, "Колумб", R.drawable.kolumb, offsetX, offsetY) // Парусник + Герой = Колумб
            185 -> Element(185, "Вампир", R.drawable.vampir, offsetX, offsetY) // Кровь + Человек = Вампир
            186 -> Element(186, "Робин Гуд", R.drawable.robin_gud, offsetX, offsetY) // Лес + Герой = Робин Гуд
            187 -> Element(187, "Пожарный", R.drawable.pozharniy, offsetX, offsetY) // Воин + Огонь = Пожарный
            188 -> Element(188, "Рыбак", R.drawable.rybak, offsetX, offsetY) // Охотник + Рыба = Рыбак
            189 -> Element(189, "Книга", R.drawable.kniga, offsetX, offsetY) // Перо + Бумага = Книга
            190 -> Element(190, "Пашня", R.drawable.pashnya, offsetX, offsetY) // Земля + Трактор = Пашня
            191 -> Element(191, "Лён", R.drawable.len, offsetX, offsetY) // Цветок + Пашня = Лён
            192 -> Element(192, "Отравленное оружие", R.drawable.otravlennoe_oruzhie, offsetX, offsetY) // Оружие + Яд = Отравленное оружие
            193 -> Element(193, "Колесо", R.drawable.koleso, offsetX, offsetY) // Инструмент + Древесина = Колесо
            194 -> Element(194, "Телега", R.drawable.telega, offsetX, offsetY) // Колесо + Древесина = Телега
            195 -> Element(195, "Упряжка", R.drawable.upryazhka, offsetX, offsetY) // Зверь + Телега = Упряжка
            196 -> Element(196, "Паровоз", R.drawable.parovoz, offsetX, offsetY) // Телега + Паровой двигатель = Паровоз
            197 -> Element(197, "Велосипед", R.drawable.velosiped, offsetX, offsetY) // Колесо + Колесо = Велосипед
            198 -> Element(198, "Колесница", R.drawable.kolesnica, offsetX, offsetY) // Воин + Телега = Колесница
            199 -> Element(199, "Солнце", R.drawable.solnce, offsetX, offsetY) // Небо + Колесница = Солнце
            200 -> Element(200, "Гладиатор", R.drawable.gladiator, offsetX, offsetY) // Воин + Колесница = Гладиатор
            201 -> Element(201, "Подсолнух", R.drawable.podsolnuh, offsetX, offsetY) // Солнце + Цветок = Подсолнух
            202 -> Element(202, "Подсолнечное масло", R.drawable.oil, offsetX, offsetY) // Подсолнух + Давление = Подсолнечное масло
            203 -> Element(203, "Машина", R.drawable.car, offsetX, offsetY) // Телега + Мотор = Машина
            204 -> Element(204, "Мотоцикл", R.drawable.motobike, offsetX, offsetY) // Велосипед + Мотор = Мотоцикл
            205 -> Element(205, "Аквариум", R.drawable.acuarium, offsetX, offsetY) // Стекло + Рыба = Аквариум
            206 -> Element(206, "Бомба", R.drawable.bomba, offsetX, offsetY) // Металл + Порох = Бомба
            207 -> Element(207, "Одежда", R.drawable.clothes, offsetX, offsetY) // Человек + Ткань = Одежда
            208 -> Element(208, "Огнестрельное оружие", R.drawable.weapon, offsetX, offsetY) // Оружие + Порох = Огнестрельное оружие
            209 -> Element(209, "Лампочка", R.drawable.lampa, offsetX, offsetY) // Электричество + Стекло = Лампочка
            210 -> Element(210, "Идея", R.drawable.idea, offsetX, offsetY) // Человек + Лампочка = Идея
            211 -> Element(211, "Свет", R.drawable.svet, offsetX, offsetY) // Электричество + Лампочка = Свет
            212 -> Element(212, "Музыка", R.drawable.music, offsetX, offsetY) // Звук + Идея = Музыка
            213 -> Element(213, "Радуга", R.drawable.raduga, offsetX, offsetY) // Вода + Свет = Радуга
            214 -> Element(214, "Кислород", R.drawable.kislorod, offsetX, offsetY) // Свет + Цветок = Кислород
            215 -> Element(215, "Уксус", R.drawable.uksus, offsetX, offsetY) // Спирт + Кислород = Уксус
            216 -> Element(216, "Гремучий газ", R.drawable.ugarniy_gaz, offsetX, offsetY) // Кислород + Водород = Гремучий газ
            217 -> Element(217, "Озон", R.drawable.ozone, offsetX, offsetY) // Кислород + Электричество = Озон
            218 -> Element(218, "Углекислый газ", R.drawable.uglekisliy_gaz, offsetX, offsetY) // Кислород + Человек = Углекислый газ
            219 -> Element(219, "Газировка", R.drawable.gazirovka, offsetX, offsetY) // Вода + Углекислый газ = Газировка
            220 -> Element(220, "Кока-кола", R.drawable.cola, offsetX, offsetY) // Газировка + Кармин = Кока-кола
            221 -> Element(221, "Новогодняя ёлка", R.drawable.elka, offsetX, offsetY) // Дерево + Лампочка = Новогодняя ёлка
            222 -> Element(222, "Библиотека", R.drawable.library, offsetX, offsetY) // Книга + Книга = Библиотека
            223 -> Element(223, "Учёный", R.drawable.scientist, offsetX, offsetY) // Библиотека + Человек = Учёный
            224 -> Element(224, "Звезда", R.drawable.star, offsetX, offsetY) // Солнце + Учёный = Звезда
            225 -> Element(225, "Космос", R.drawable.space, offsetX, offsetY) // Звезда + Небо = Космос
            226 -> Element(226, "Пенициллин", R.drawable.penicilin, offsetX, offsetY) // Плесень + Учёный = Пенициллин
            227 -> Element(227, "Колибри", R.drawable.calibri, offsetX, offsetY) // Птица + Цветок = Колибри
            else -> null
        }
    }

    //метод удаления всех элементов
    fun clearElements() {
        _elementsOnScreen.clear()
    }
    fun updateElementPosition(element: Element, newX: Float, newY: Float) { //благодаря apply найденный объект - получатель, без this можно обращаться
        _elementsOnScreen.find { it.id == element.id }?.apply {
            offsetX = newX
            offsetY = newY
        }
    }

}