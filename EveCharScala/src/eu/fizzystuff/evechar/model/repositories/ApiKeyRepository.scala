package eu.fizzystuff.evechar.model.repositories
import eu.fizzystuff.evechar.model.domain.ApiKey
import scala.collection.mutable.ArrayBuffer
import android.database.Cursor
import android.content.Context
import android.database.sqlite.SQLiteDatabase

class ApiKeyRepository extends SQLiteRepository[ApiKey] with ReadWriteRepository[ApiKey] {
  
	var db = null
  
    def ApiKeyRepository(db: SQLiteDatabase) {
      this.db = db
    }
  
	def GetAll(callback: (Seq[ApiKey]) => Unit) {
	  var list = GetAll("SELECT KeyId, VerificationCode FROM ApiKey", new ArrayBuffer[String], c => {
	      new ApiKey() {
	      KeyId = c.getString(0)
	      VerificationCode = c.getString(1)
	    }
	  });
	  
	  callback(list)
	}
	
	
	
}