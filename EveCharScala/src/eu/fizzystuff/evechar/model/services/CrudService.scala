package eu.fizzystuff.evechar.model.services

trait CrudService[T] {
	def GetAll(callback: () => Seq[T])
	
}