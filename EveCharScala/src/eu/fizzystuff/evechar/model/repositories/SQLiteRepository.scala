package eu.fizzystuff.evechar.model.repositories
import android.database.sqlite.SQLiteDatabase
import android.database.Cursor
import scala.collection.mutable.LinkedList
import scala.collection.mutable.ArrayBuffer
import eu.fizzystuff.evechar.utils.Helpers
import android.content.ContentValues

trait SQLiteRepository[T <: AnyRef] {
  import Helpers._
  var db: SQLiteDatabase = null

  def GetAll(sql: String, params: Seq[String]): Seq[T] = {
    val list = new ArrayBuffer[T]

    using(db.rawQuery(sql, params.toArray(classManifest[String])))(cursor => {
      cursor.moveToFirst()

      while (!cursor.isAfterLast()) {
        list += CursorToEntity(cursor)
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
  def CursorToEntity(c: Cursor): T
}