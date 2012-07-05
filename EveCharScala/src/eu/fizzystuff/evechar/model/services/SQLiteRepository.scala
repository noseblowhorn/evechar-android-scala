package eu.fizzystuff.evechar.model.services
import android.database.sqlite.SQLiteDatabase
import android.database.Cursor
import scala.collection.mutable.LinkedList
import scala.collection.mutable.ArrayBuffer
import eu.fizzystuff.evechar.utils.Helpers

trait SQLiteRepository[T <: AnyRef] {
    import Helpers._
	var db: SQLiteDatabase
	
	def GetById(sql: String, id: Int, mapper: Cursor => T): T = {
	  var result: T = None.get
	  
	  using(db.rawQuery(sql, Array(Integer.toString(id) )))(cursor => {
	    cursor.moveToFirst()
	    result = mapper(cursor)
	  })
	  
	  return result
	}
	
	def GetAll(sql: String, params: Seq[String], mapper: Cursor => T): Seq[T] = {
	  val list = new ArrayBuffer[T]
	  
	  using(db.rawQuery(sql, params.toArray(classManifest[String])))(cursor => {
	    cursor.moveToFirst()
	    
	    while (!cursor.isAfterLast())
	    {
		  list += mapper(cursor)
		  cursor.moveToNext()
	    }
	  })
	  
	  return list
	} 

}