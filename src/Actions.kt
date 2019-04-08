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