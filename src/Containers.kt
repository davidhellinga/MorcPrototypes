
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