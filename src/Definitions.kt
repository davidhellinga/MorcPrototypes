
val classNameMap = hashMapOf(
    "Card" to Card::class,
    "MagicCard" to MagicCard::class,
    "Deck" to Deck::class,
    "Move" to Move::class
)

fun convertToBaseType(type: String, value: String): Any {
    return when (type) {
        "String" -> value
        "Int" -> value.toInt()
        "ICardContainer" -> ActionFromTxt.cardContainers.find { it.name == value }
            ?: throw Exception("Card container not found with name: $value")
        "ICard" -> ActionFromTxt.cards.find { it.name == value }
            ?: throw Exception("Card not found with name: $value")
//        "MutableList<String>" ->{
//            value.replace(Regex("[{} ]"), "")
//            value.split(",").toMutableList()
//        }
        else -> throw IllegalArgumentException("Input is not a class type defined for conversion at: $type $value")
    }
}