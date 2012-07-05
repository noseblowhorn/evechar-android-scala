package eu.fizzystuff.evechar.utils

object Helpers {
	def using[C <: { def close() }](c: C)(f: C => Unit) {
		try { f(c) } finally { c.close() }
	}
}