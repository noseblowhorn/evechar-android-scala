package eu.fizzystuff.evechar.model.repositories
import android.database.sqlite.SQLiteDatabase
import android.database.Cursor
import scala.collection.mutable.LinkedList
import scala.collection.mutable.ArrayBuffer
import eu.fizzystuff.evechar.utils.Helpers
import android.content.ContentValues

trait SQLiteRepository[T <: AnyRef] {
  import Helpers._
  var db: SQLiteDatabase

  def GetAll(sql: String, params: Seq[String], mapper: Cursor => T): Seq[T] = {
    val list = new ArrayBuffer[T]

    using(db.rawQuery(sql, params.toArray(classManifest[String])))(cursor => {
      cursor.moveToFirst()

      while (!cursor.isAfterLast()) {
        list += mapper(cursor)
        cursor.moveToNext()
      }
    })

    return list
  }

  def Insert(table: String, entity: T) {
    db.insertOrThrow(table, null, EntityToContentValues(entity))
  }

  def Update(table: String, whereClause: String, entity: T) {
    db.update(table, EntityToContentValues(entity), whereClause, EntityWhereArgs(entity))
  }
  
  def Remove(table: String, whereClause: String, entity: T) {
    db.delete(table, whereClause, EntityWhereArgs(entity))
  }

  def EntityToContentValues(entity: T): ContentValues
  def EntityWhereArgs(entity: T): Array[String]
  
  //	def GetById(sql: String, id: Int, mapper: Cursor => T): T = {
  //	  var result: T = None.get
  //	  
  //	  using(db.rawQuery(sql, Array(Integer.toString(id) )))(cursor => {
  //	    cursor.moveToFirst()
  //	    result = mapper(cursor)
  //	  })
  //	  
  //	  return result
  //	}
}