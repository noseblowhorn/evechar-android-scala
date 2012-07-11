package eu.fizzystuff.evechar.model.repositories

trait ReadOnlyRepository[T] {
	def GetAll(callback: (Seq[T]) => Unit)
}