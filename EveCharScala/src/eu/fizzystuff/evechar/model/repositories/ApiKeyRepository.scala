package eu.fizzystuff.evechar.model.repositories
import eu.fizzystuff.evechar.model.domain.ApiKey
import scala.collection.mutable.ArrayBuffer
import android.database.Cursor
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.content.ContentValues

class ApiKeyRepository extends SQLiteRepository[ApiKey] with ReadWriteRepository[ApiKey] {
  
	var db = null
	
	val SelectSql = "SELECT KeyId, VerificationCode FROM ApiKey"
	val TableName = "ApiKeys"
    val WhereClause = "Id = ?"
  
    def ApiKeyRepository(db: SQLiteDatabase) {
      this.db = db
    }
  
	def GetAll(callback: (Seq[ApiKey]) => Unit) {
	  
	  var list = GetAll(SelectSql, new ArrayBuffer[String], c => {
	      new ApiKey() {
	      KeyId = c.getString(0)
	      VerificationCode = c.getString(1)
	    }
	  });
	  
	  callback(list)
	}
	
	def EntityToContentValues(entity: ApiKey): ContentValues = {
	  var vals = new ContentValues
	  vals.put("KeyId", entity.KeyId)
	  vals.put("VerificationCode", entity.VerificationCode)
	  
	  return vals
	}
	
	def EntityWhereArgs(entity: ApiKey): Array[String] = {
	  Array(entity.Id.toString())
	}
	
	def Insert(entity: ApiKey) {
	  Insert(TableName, entity)
	}
	
	def Update(entity: ApiKey) {
	  Update(TableName, WhereClause, entity)
	}
	
	def Delete(entity: ApiKey) {
	  Remove(TableName, WhereClause, entity)
	}
}