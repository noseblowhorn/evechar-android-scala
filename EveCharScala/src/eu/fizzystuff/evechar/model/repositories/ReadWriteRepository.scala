package eu.fizzystuff.evechar.model.repositories

trait ReadWriteRepository[T] extends ReadOnlyRepository[T] {
	def Insert(entity: T)
	def Update(entity: T)
	def Delete(entity: T)
}