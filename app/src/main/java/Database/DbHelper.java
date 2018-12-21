package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.praba.prakmob.model.Diary;
import com.example.praba.prakmob.model.DiaryShow;

import java.util.ArrayList;
import java.util.List;

import static com.example.praba.prakmob.model.DiaryShow.Entry.COLUMN_IDUSER;
import static com.example.praba.prakmob.model.DiaryShow.Entry.TABLE_NAME;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "diary.sql";
    private static final int DATABASE_VERSION = 1;

    public DbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE "+ TABLE_NAME+" ("
                +DiaryShow.Entry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +DiaryShow.Entry.COLUMN_ID+" INTEGER,"
                +DiaryShow.Entry.COLUMN_TITLE+" TEXT,"
                +DiaryShow.Entry.COLUMN_DIARY+" TEXT,"
                +DiaryShow.Entry.COLUMN_IMAGE+" TEXT,"
                +COLUMN_IDUSER+" TEXT);";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE "+TABLE_NAME+";";
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public long insertDiary(int id, String title, String diary, String image, String id_user){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DiaryShow.Entry.COLUMN_ID, id);
        contentValues.put(DiaryShow.Entry.COLUMN_TITLE, title);
        contentValues.put(DiaryShow.Entry.COLUMN_DIARY, diary);
        contentValues.put(DiaryShow.Entry.COLUMN_IMAGE, image);
        contentValues.put(COLUMN_IDUSER, id_user);

        long lastId = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        return lastId;
    }

    public List<DiaryShow> selectDiary(String id_user){
        List<DiaryShow> diaryList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME
                + " WHERE " + COLUMN_IDUSER + " ="+id_user+";";
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);

        int count = cursor.getCount();
        if(count>0){
            cursor.moveToFirst();
            while (cursor.moveToNext()){
                String id = cursor.getString(cursor.getColumnIndex(DiaryShow.Entry.COLUMN_ID));
                String title = cursor.getString(cursor.getColumnIndex(DiaryShow.Entry.COLUMN_TITLE));
                String diary = cursor.getString(cursor.getColumnIndex(DiaryShow.Entry.COLUMN_DIARY));
                String image = cursor.getString(cursor.getColumnIndex(DiaryShow.Entry.COLUMN_IMAGE));

                DiaryShow temp = new DiaryShow(id, title, diary,image);
                diaryList.add(temp);
            }
        }
        cursor.close();
        return diaryList;
    }

    public void deleteAllDiary(String id_user){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(DiaryShow.Entry.TABLE_NAME,DiaryShow.Entry.COLUMN_IDUSER+"=?", new String[]{id_user});
    }
}
