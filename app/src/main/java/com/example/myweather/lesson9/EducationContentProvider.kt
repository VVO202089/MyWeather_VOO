package com.example.myweather.lesson9

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.myweather.MyApp.Companion.getHistoryDAO
import com.example.myweather.R
import com.example.myweather.room.HistoryDAO
import com.example.myweather.room.HistoryEntity

const val ENTITY_PATH = "HistoryEntity"
const val URI_ALL = 0
const val URI_ID = 1

const val ID = "id"
const val NAME = "name"
const val TEMP = "temp"

class EducationContentProvider : ContentProvider() {

    private lateinit var uriMatcher: UriMatcher

    private var entityContentType: String? = null
    private var entityContentItemType: String? = null
    private lateinit var contentUri: Uri

    override fun onCreate(): Boolean {

        val autority = context?.resources?.getString(R.string.avtority)
        uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        uriMatcher.addURI(autority, ENTITY_PATH, URI_ALL)
        uriMatcher.addURI(autority, "$ENTITY_PATH/#", URI_ID)
        // Тип содержимого — все объекты
        entityContentType = "vnd.android.cursor.dir/vnd.$autority.$ENTITY_PATH"
        // Тип содержимого — один объект
        entityContentItemType = "vnd.android.cursor.item/vnd.$autority.$ENTITY_PATH"
        // Строка для доступа к Provider
        contentUri = Uri.parse("content://$autority/$ENTITY_PATH")

        return true

    }

    override fun query(
        p0: Uri,
        p1: Array<out String>?,
        p2: String?,
        p3: Array<out String>?,
        p4: String?
    ): Cursor? {
        val historyDAO = getHistoryDAO() // ссылка на БД
        val cursor = when (uriMatcher.match(p0)) {
            URI_ALL -> {
                historyDAO.getHistoryCursor()
            }
            URI_ID -> {
                val id = ContentUris.parseId(p0)
                historyDAO.getHistoryCursor()
            }
            else -> throw IllegalStateException("это невозможно")
        }
        cursor.setNotificationUri(context?.contentResolver, p0)
        return cursor
    }

    override fun getType(p0: Uri): String? {
        return when (uriMatcher.match(p0)) {
            URI_ALL -> {
                entityContentType
            }
            URI_ID -> {
                entityContentItemType
            }
            else -> throw IllegalStateException("это невозможно")
        }
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        require(uriMatcher.match(p0) == URI_ALL) {
            "NEED entityContentType, wrong uri $p0"
        }
        val historyDAO: HistoryDAO = getHistoryDAO()
        val entity = map(p1)
        historyDAO.insert(entity)
        val resultUri = ContentUris.withAppendedId(p0, entity.id)
        // явное изменение в журнале
        context?.contentResolver?.notifyChange(resultUri, null)
        return resultUri
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        require(uriMatcher.match(p0) == URI_ALL) {
            "NEED entityContentType, wrong uri $p0"
        }
        val historyDAO: HistoryDAO = getHistoryDAO()
        val id = ContentUris.parseId(p0)
        historyDAO.deleteID(id)
        context?.contentResolver?.notifyChange(p0, null)
        return 1
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        require(uriMatcher.match(p0) == URI_ID) {
            "NEED entityContentItemType, wrong uri $p0"
        }
        val historyDAO: HistoryDAO = getHistoryDAO()
        val entity = map(p1)
        historyDAO.update(entity)
        context?.contentResolver?.notifyChange(p0, null)
        return 1
    }

    fun map(values: ContentValues?): HistoryEntity {

        return if (values == null) {
            return HistoryEntity()
        } else {

            val id = if (values.containsKey(ID)) values[ID] as Long else 0
            val name = if (values.containsKey(NAME)) values[NAME] as String else ""
            val temp = if (values.containsKey(TEMP)) values[TEMP] as Int else 0
            val feelsLike = if (values.containsKey(TEMP)) values[TEMP] as Int else 0
            val condition = if (values.containsKey(NAME)) values[NAME] as String else ""
            val pressuremm = if (values.containsKey(NAME)) values[NAME] as Int else 0
            val windSpeed = if (values.containsKey(NAME)) values[NAME] as Double else 0.0

            HistoryEntity(id, name, temp, feelsLike, condition, pressuremm, windSpeed)
        }

    }
}