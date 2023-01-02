package com.example.myquiz;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myquiz.QuizContract.*;

import java.util.ArrayList;
import java.util.List;


public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MyAwesomeQuiz.db";
    private static final int DATABASE_VERSION = 1;

    private static QuizDbHelper instance;

    private SQLiteDatabase db;

    private QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized QuizDbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new QuizDbHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_CATEGORIES_TABLE = "CREATE TABLE " +
                CategoriesTable.TABLE_NAME + "( " +
                CategoriesTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CategoriesTable.COLUMN_NAME + " TEXT " +
                ")";

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION4 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER, " +
                QuestionsTable.COLUMN_DIFFICULTY + " TEXT, " +
                QuestionsTable.COLUMN_CATEGORY_ID + " INTEGER, " +
                "FOREIGN KEY(" + QuestionsTable.COLUMN_CATEGORY_ID + ") REFERENCES " +
                CategoriesTable.TABLE_NAME + "(" + CategoriesTable._ID + ")" + "ON DELETE CASCADE" +
                ")";

        db.execSQL(SQL_CREATE_CATEGORIES_TABLE);
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillCategoriesTable();
        fillQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CategoriesTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    private void fillCategoriesTable() {
        Category c1 = new Category("Programming");
        insertCategory(c1);
        Category c2 = new Category("Aptitude");
        insertCategory(c2);
        Category c3 = new Category("SQL");
        insertCategory(c3);
        Category c4 = new Category("CN");
        insertCategory(c4);
    }

    public void addCategory(Category category) {
        db = getWritableDatabase();
        insertCategory(category);
    }

    public void addCategories(List<Category> categories) {
        db = getWritableDatabase();

        for (Category category : categories) {
            insertCategory(category);
        }
    }

    private void insertCategory(Category category) {
        ContentValues cv = new ContentValues();
        cv.put(CategoriesTable.COLUMN_NAME, category.getName());
        db.insert(CategoriesTable.TABLE_NAME, null, cv);
    }

    private void fillQuestionsTable() {
        Question q1 = new Question(
                "Which of the following statements are correct ?\n"+
                "1:\tA string is a collection of characters terminated by '\\0'.\n" +
                "2:\tThe format specifier %s is used to print a string.\n" +
                "3:\tThe length of the string can be obtained by strlen().\n" +
                "4:\tThe pointer CANNOT work on string.",
                "1, 2",
                "1, 2, 3",
                "2, 4",
                "3, 4",
                2, Question.DIFFICULTY_EASY, Category.PROGRAMMING);
        insertQuestion(q1);

        Question q2 = new Question("Assume int is 4 bytes, char is 1 byte and float is 4 bytes. Also, assume that pointer size is 4 bytes (i.e. typical case)\n"+
                "char *pChar;\t"+
                "int *pInt;\t"+
                "float *pFloat;\t"+
                "sizeof(pChar);\t"+
                "sizeof(pInt);\t"+
                "sizeof(pFloat);\n"+
                "What’s the size returned for each of sizeof() operator?",
                "4 4 4", "1 4 4", "1 4 8","None of the above",
                1, Question.DIFFICULTY_EASY, Category.PROGRAMMING);
        insertQuestion(q2);

        Question q3 = new Question("Which of the following statement is True?",
                "User has to explicitly define the numeric value of enumerations",
                "User has a control over the size of enumeration variables",
                "Enumeration can have an effect local to the block, if desired",
                "Enumerations have a global effect throughout the file",
                3, Question.DIFFICULTY_EASY, Category.PROGRAMMING);
        insertQuestion(q3);

        Question q4 = new Question("Which of the following operations can be performed on the file \"NOTES.TXT\" using the below code?\n" +
                "FILE *fp;\n" +
                "fp = fopen(\"NOTES.TXT\", \"r+\");",
                "Reading", "Writing", "Appending", "Read and Write",
                4, Question.DIFFICULTY_EASY, Category.PROGRAMMING);
        insertQuestion(q4);

        Question q5 = new Question("Which of the following function sets first n characters of a string to a given character?",
                "strinit()", "strnset()", "strset()","strcset()", 2,
                Question.DIFFICULTY_EASY, Category.PROGRAMMING);
        insertQuestion(q5);

        Question q6 = new Question("Which of the following range is a valid long double (Turbo C in 16 bit DOS OS) ?",
                "3.4E^-4932 to 1.1E^+4932", "3.4E^-4932 to 3.4E^+4932", "1.1E^-4932 to 1.1E^+4932","1.7E^-4932 to 1.7E^+4932", 1,
                Question.DIFFICULTY_MEDIUM, Category.PROGRAMMING);
        insertQuestion(q6);

        Question q7 = new Question("What will be the output of the program ?\n"+
                "#include<stdio.h>\n" +
                "int main() {\n" +
                "    float arr[] = {12.4, 2.3, 4.5, 6.7};\n" +
                "    printf(\"%d\\n\", sizeof(arr)/sizeof(arr[0]));\n" +
                "    return 0;}",
                "5", "4", "6","7", 2,
                Question.DIFFICULTY_MEDIUM, Category.PROGRAMMING);
        insertQuestion(q7);

        Question q8 = new Question("What will be the output of the program (sample.c) given below if it is executed from the command line?\n" +
                "cmd> sample friday tuesday sunday\n" +
                "/* sample.c */\n" +
                "#include<stdio.h>\n" +
                "int main(int argc, char *argv[]) {\n" +
                "    printf(\"%c\", **++argv);\n" +
                "    return 0;\n}",
                "s", "f", "sample","friday", 2,
                Question.DIFFICULTY_MEDIUM, Category.PROGRAMMING);
        insertQuestion(q8);

        Question q9 = new Question("What is the output of the program\n"+
                "#include<stdio.h>\n" +
                "int main() {\n" +
                "    int a[5] = {2, 3};\n" +
                "    printf(\"%d, %d, %d\\n\", a[2], a[3], a[4]);\n" +
                "    return 0;}",
                "Garbage Values", "2, 3, 3", "3, 2, 2","0, 0, 0", 4,
                Question.DIFFICULTY_MEDIUM, Category.PROGRAMMING);
        insertQuestion(q9);

        Question q10 = new Question("What will be the output of the program?\n"+
                "#include<stdio.h>\n"+
                "int main() {\n"+
                "const int i=0;\n"+
                "printf(\"%d\n\", i++);\n"+
                "return 0;\n}",
                "10", "11", "No Output","Error: ++needs a value", 4,
                Question.DIFFICULTY_MEDIUM, Category.PROGRAMMING);
        insertQuestion(q10);

    }

    public void addQuestion(Question question) {
        db = getWritableDatabase();
        insertQuestion(question);
    }

    public void addQuestions(List<Question> questions) {
        db = getWritableDatabase();

        for (Question question : questions) {
            insertQuestion(question);
        }
    }

    private void insertQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_OPTION4, question.getOption4());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        cv.put(QuestionsTable.COLUMN_DIFFICULTY, question.getDifficulty());
        cv.put(QuestionsTable.COLUMN_CATEGORY_ID, question.getCategoryID());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    @SuppressLint("Range")
    public List<Category> getAllCategories() {
        List<Category> categoryList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + CategoriesTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Category category = new Category();
                category.setId(c.getInt(c.getColumnIndex(CategoriesTable._ID)));
                category.setName(c.getString(c.getColumnIndex(CategoriesTable.COLUMN_NAME)));
                categoryList.add(category);
            } while (c.moveToNext());
        }

        c.close();
        return categoryList;
    }

    @SuppressLint("Range")
    public ArrayList<Question> getAllQuestions() {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(c.getInt(c.getColumnIndex(QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION4)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                question.setCategoryID(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_CATEGORY_ID)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }

    @SuppressLint("Range")
    public ArrayList<Question> getQuestions(int categoryID, String difficulty) {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();

        String selection = QuestionsTable.COLUMN_CATEGORY_ID + " = ? " +
                " AND " + QuestionsTable.COLUMN_DIFFICULTY + " = ? ";
        String[] selectionArgs = new String[]{String.valueOf(categoryID), difficulty};

        Cursor c = db.query(
                QuestionsTable.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(c.getInt(c.getColumnIndex(QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION4)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                question.setCategoryID(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_CATEGORY_ID)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }
}