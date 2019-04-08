import java.io.File

fun main(args: Array<String>) {
    val main = Main()
    main.call(args)

}

class Main {
    companion object {
        val actionFromTxt = ActionFromTxt()
    }

    fun call(args: Array<String>) {
        when (args[0]) {
            "class" -> classFromTxt()
            "action" -> actionFromTxt.call()
        }
        for (action in ActionFromTxt.actions) {
            action.call()
        }
        for (cardContainer in ActionFromTxt.cardContainers) {
            println(cardContainer.toString())
        }

    }
}

/**
 * Reads file from resources folder based on file name, returns list of lines from file
 */
fun readFileAsLines(fileName: String): List<String> = File(fileName).useLines { it.toList() }

interface ICard {
    val name: String
    val container: ICardContainer
}

class Card(override val name: String, private val value: Int, override val container: ICardContainer) : ICard {
    override fun toString(): String {
        return "$name is worth $value points and is currently in ${container.name}"
    }
}

class MagicCard(
    override val name: String,
    private val manacost: Int,
    private val power: Int,
    private val toughness: Int, override val container: ICardContainer
) : ICard {
    override fun toString(): String {
        return "$name costs $manacost to cast, its power/toughness is $power/$toughness and is currently in ${container.name}"
    }
}

//Change this to abstract class
interface ICardContainer {
    var name: String
    var cards: MutableList<ICard>
}

class Deck(override var name: String) : ICardContainer {
    override var cards: MutableList<ICard> = mutableListOf()
    override fun toString(): String {
        var str = "Deck $name contains:"
        for (card in cards)
            str += " ${card.name},"
        return str
    }
}

interface IAction {
    fun call()
}

class Move(private val source: ICardContainer, private val target: ICardContainer, private val card: ICard) : IAction {
    override fun call() {
        if (!source.cards.contains(card)) {
            throw Exception("Container ${source.name} does not contain ${card.name}")
        }
        source.cards.remove(card)
        target.cards.add(card)
    }

    override fun toString(): String {
        return "Moving ${card.name} from ${source.name} to ${target.name}"
    }
}

val classNameMap = hashMapOf(
    "Card" to Card::class,
    "MagicCard" to MagicCard::class,
    "Deck" to Deck::class,
    "Move" to Move::class
)

fun convertToBaseType(type: String, value: String): Any {
    return when (type) {
        "String" -> value.toString()
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