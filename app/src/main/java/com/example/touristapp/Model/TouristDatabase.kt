package com.example.touristapp.Model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

private val DatabaseName = "TouristDatabase.db"
private val ver : Int = 1

class TouristDatabase(context: Context) : SQLiteOpenHelper(context,DatabaseName, null, ver) {

    // Tourist Table
    private val touristTableName = "TouristTable"
    private val column_UserID ="UserId"
    private val column_Firstname = "Firstname"
    private val column_Lastname = "Lastname"
    private val column_Username = "Username"
    private val column_Email = "Email"

    // password Table
    public val passwordTableName = "PasswordTable"
    public val column_Password = "Password"
    public val column_PUserId = "UserId"
    public val column_PasswordId = "PasswordId"

    // Future Interest Table
    private val futureInterestTableName = "FutureInterests"
    private val columnName = "Name"
    private val columnCountry = "Country"
    private val columnCityTown = "CityTown"
    private val columnDescriptions = "Description"
    private val columnBooked = "Booked"
    private val columnUserMapTitle = "UserMapTitle"
    private val columnFutureLocationId = "FutureLocationId"
    private val columnUserId = "UserId"
    private val columnLatitude = "Latitude"
    private val columnLongitude = "Longitude"

    // Review Table
    private val reviewTableName = "ReviewTable"
    private val columnReviewTitle = "Title"
    private val columnReviewDescription = "Description"
    private val columnReviewRating = "Rating"
    private val columnReviewCountry = "Country"
    private val columnReviewImage = "Image"
    private val columnReviewId = "ReviewId"
    private val columnReviewUsername = "Username"
    private val columnReviewUserId = "Userid"

    //Report Table
    private val reportTableName = "ReportTable"
    private val columnReportId = "ReportId"
    private val columnReportUsername = "Username"
    private val columnReportDescription = "Description"
    private val columnReportType = "Type"
    private val columnReportUserId = "UserId"

    // Memories Table
    private val memoriesTableName = "MemoriesTable"
    private val columnMemoriesId = "MemoriesId"
    private val columnMemoriesLocation = "Location"
    private val columnMemoriesUserId = "UserId"
    private val columnMemoriesImage = "Image"

    /**
     * Create each table for the tourist database
     */
    override fun onCreate(db: SQLiteDatabase?) {

        try {
            val sqlCreateStatement: String = " CREATE TABLE " + touristTableName + " ( " + column_UserID +
                    " INTEGER PRIMARY KEY AUTOINCREMENT, " + column_Username + " TEXT NOT NULL, " + column_Firstname + " TEXT, " +
                    column_Lastname + " TEXT, " + column_Email + " TEXT NOT NULL )"

            db?.execSQL(sqlCreateStatement)

            val sqlCreateStatement2: String = " CREATE TABLE " + passwordTableName + " ( " + column_PasswordId + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    column_Password + " TEXT, " + column_PUserId + " INTEGER NOT NULL )"

            db?.execSQL(sqlCreateStatement2)

            val sqlCreateStatement3: String = "CREATE TABLE " + reviewTableName + " ( " + columnReviewId +
                    " INTEGER PRIMARY KEY AUTOINCREMENT, "  + columnReviewTitle + " TEXT NOT NULL, " +
                    columnReviewDescription + " TEXT NOT NULL, " + columnReviewRating + " REAL NOT NULL, " +
                    columnReviewCountry + " TEXT NOT NULL, " + columnReviewUsername + " TEXT NOT NULL, " +
                    columnReviewUserId + " INTEGER " + columnReviewImage + " BLOB )"

            db?.execSQL(sqlCreateStatement3)

            val sqlCreateStatement4: String = "CREATE TABLE " + futureInterestTableName + " ( " + columnFutureLocationId +
                    " INTEGER PRIMARY KEY AUTOINCREMENT " + columnName + " TEXT, " + columnDescriptions + " TEXT " + columnCountry +
                    " TEXT, " + columnCityTown + " TEXT, " + columnBooked + " TEXT, " + columnUserMapTitle +
                    " TEXT, " + columnUserId + " INTEGER" + columnLatitude + " TEXT, " + columnLongitude + " TEXT )"

            db?.execSQL(sqlCreateStatement4)

            val sqlCreateStatement5: String = "CREATE TABLE " + reportTableName + " ( " + columnReportId +
                    " INTEGER PRIMARY KEY AUTOINCREMENT " + columnReportUsername + " TEXT " + columnReportDescription + " TEXT " +
                    columnReportType + " TEXT " +  columnReportUserId + " INTEGER )"

            db?.execSQL(sqlCreateStatement5)

            val sqlCreateStatement6: String = "CREATE TABLE " + memoriesTableName + " ( " + columnMemoriesId +
                    " INTEGER PRIMARY KEY AUTOINCREMENT, " + columnMemoriesLocation + " TEXT " + columnMemoriesUserId + " INTEGER" +
                    columnMemoriesImage + " BLOB )"

            db?.execSQL(sqlCreateStatement6)

        }
        catch (e: SQLException){

        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    /**
     * adds the user information to the tourist table
     */
    fun addTourist(tourist: Tourist): Boolean   {

        val db: SQLiteDatabase = this.writableDatabase
        val cv:ContentValues = ContentValues()

        cv.put(column_Username, tourist.userID)
        cv.put(column_Firstname, tourist.firstname)
        cv.put(column_Lastname, tourist.lastname)
        cv.put(column_Email, tourist.email)

        val success = db.insert(touristTableName, null, cv)
        db.close()
        return success != -1L
    }
    fun addPassword(password: Password): Boolean   {

        val db: SQLiteDatabase = this.writableDatabase
        val cv:ContentValues = ContentValues()

        cv.put(column_Password, password.password)
        cv.put(column_PUserId, password.userId)


        val success = db.insert(passwordTableName, null, cv)
        db.close()
        return success != -1L
    }

    fun getPasswordUser(tId: Int): Password  {
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $passwordTableName WHERE $column_PUserId == $tId"

        val cursor: Cursor = db.rawQuery(sqlStatement, null)
        if  (cursor.moveToFirst()){
            db.close()

            return Password(cursor.getInt(0),
                cursor.getString(1),
                cursor.getInt(2))
        } else{
            db.close()
            return Password(0, "",0)

        }
    }
    /**
     * Adds a review to review table
     */
    fun addReview(review: Review): Boolean{
        val db: SQLiteDatabase = this.writableDatabase
        val cv: ContentValues = ContentValues()

        cv.put(columnReviewTitle,review.title)
        cv.put(columnReviewDescription,review.description)
        cv.put(columnReviewCountry,review.countryReview)
        cv.put(columnReviewRating,review.rating)
        cv.put(columnReviewImage,review.image)
        cv.put(columnReviewUsername,review.username)
        cv.put(columnReviewUserId,review.userId)


        val success = db.insert(reviewTableName, null,cv)
        db.close()
        return success != -1L
    }


    fun addReport(report: Report): Boolean{
        val db: SQLiteDatabase = this.writableDatabase
        val cv: ContentValues = ContentValues()

        cv.put(columnReportUsername,report.username)
        cv.put(columnReportDescription,report.description)
        cv.put(columnReportUserId,report.userId)
        cv.put(columnReportType,report.type)

        val success = db.insert(reportTableName, null,cv)
        db.close()
        return success != -1L
    }

    fun addMemory(memories: Memories): Boolean{
        val db: SQLiteDatabase = this.writableDatabase
        val cv: ContentValues = ContentValues()

        cv.put(columnMemoriesLocation,memories.location)
        cv.put(columnMemoriesUserId,memories.userId)
        cv.put(columnMemoriesImage,memories.image)

        val success = db.insert(memoriesTableName, null,cv)
        db.close()
        return success != -1L
    }

    /**
     * adds the already hashed password to the password table
     */

    fun addpassword(password: Password): Boolean {

        val db: SQLiteDatabase = this.writableDatabase
        val cv: ContentValues = ContentValues()

        cv.put(column_Password, password.password)
        cv.put(column_UserID, password.userId)

        val success = db.insert(passwordTableName, null, cv)
        db.close()
        return success != -1L
    }

    /**
     * adds the users future interest to the future interest table
     *
     */

    fun addFutureInterest(userMap: UserMap): Boolean {

        val db: SQLiteDatabase = this.writableDatabase
        val cv: ContentValues = ContentValues()

        cv.put(columnName,userMap.places[0].name)
        cv.put(columnDescriptions,userMap.places[0].description)
        cv.put(columnCountry, userMap.places[0].country)
        cv.put(columnCityTown,userMap.places[0].city_town)
        cv.put(columnBooked,userMap.places[0].booked)
        cv.put(columnUserMapTitle, userMap.title)
        cv.put(columnUserId,userMap.places[0].userId)
        cv.put(columnLongitude,userMap.places[0].longitude)
        cv.put(columnLatitude,userMap.places[0].latitude)
        val success = db.insert(futureInterestTableName, null, cv)
        db.close()
        return success != -1L
    }

    fun getMemory(tId: Int): Memories  {
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $memoriesTableName WHERE $columnMemoriesUserId == $tId"

        val cursor: Cursor = db.rawQuery(sqlStatement, null)
        if  (cursor.moveToFirst()){
            db.close()

            return Memories(cursor.getString(1),
                cursor.getBlob(3),
                cursor.getInt(0),
                cursor.getInt(2))
        } else{
            db.close()
            return Memories("can't get location", byteArrayOf(),0,0)

        }
    }


    /**
     * gets the user information from the database
     */
    fun getTourist(tId: Int): Tourist  {
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $touristTableName WHERE $column_UserID == $tId"

        val cursor: Cursor = db.rawQuery(sqlStatement, null)
        if  (cursor.moveToFirst()){
            db.close()

            return Tourist(cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4))
        } else{
            db.close()
            return Tourist (0, "Username not found", "firstname not found",
                "lastname not found", "email not found")

        }
    }

    /**
     * Gets all of the tourists in the table
     */
    fun getAllTourist(): ArrayList<Tourist>  {
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $touristTableName"
        var tourList = ArrayList<Tourist>()

        val cursor: Cursor = db.rawQuery(sqlStatement, null)
        if  (cursor.moveToFirst())
            do{
                val id = cursor.getInt(0)
                val username = cursor.getString(1)
                val firstname = cursor.getString(2)
                val lastname = cursor.getString(3)
                val email = cursor.getString(4)
                var tourist = Tourist(id,username,firstname,lastname,email)
                tourList.add(tourist)
            } while (cursor.moveToNext())

        db.close()
        cursor.close()

        return tourList
    }


    fun deleteTourist(tourist:Tourist):Boolean {
        val db: SQLiteDatabase = this.writableDatabase

        val result = db.delete(touristTableName, "$column_UserID = ${tourist.userID}", null) == 1

        db.close()
        return result
    }

    /**
     * This will collect everything from the review table and return all of the reviews in an arraylist
     */
    fun getAllReview(): ArrayList<Review> {
        val reviewList = ArrayList<Review>()
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $reviewTableName"

        val cursor: Cursor = db.rawQuery(sqlStatement,null)

        if (cursor.moveToFirst())
            do{
                val title: String = cursor.getString(0)
                val description: String = cursor.getString(1)
                val rating: Float = cursor.getFloat(2)
                val countryReview: String = cursor.getString(3)
                val image: ByteArray = cursor.getBlob(4)
                val username: String = cursor.getString(5)
                val reviewId: Int = cursor.getInt(6)
                val userid: Int = cursor.getInt(7)

                val review = Review(username,title,description,rating,countryReview,image,reviewId,userid)
                reviewList.add(review)
            } while (cursor.moveToNext())

        cursor.close()
        db.close()

        return reviewList
    }
    /**
     * This will collect everything from the review table and return all of the reviews in an arraylist
     */
    fun getImageReview(tId: Int): Review {
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT $columnReviewImage FROM $reviewTableName WHERE $columnReviewId == $tId"
        val cursor: Cursor = db.rawQuery(sqlStatement,null)

        if (cursor.moveToFirst())
            do{
                val image: ByteArray = cursor.getBlob(0)


                val review = Review("username","title","description",2.0f,"countryReview",image,1,1)
            } while (cursor.moveToNext())


        cursor.close()
        db.close()

        return Review("username","title","description",2.0f,"countryReview",
            byteArrayOf(),1,1)
    }

    fun getTextReview(): ArrayList<Review> {
        val reviewList = ArrayList<Review>()
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement =
            "SELECT $columnReviewTitle, $columnReviewDescription, $columnReviewRating, $columnReviewCountry, $columnReviewUsername, $columnReviewId, $columnReviewUserId FROM $reviewTableName"

        val cursor: Cursor = db.rawQuery(sqlStatement, null)

        if (cursor.moveToFirst())
            do {
                val title: String = cursor.getString(0)
                val description: String = cursor.getString(1)
                val rating: Float = cursor.getFloat(2)
                val countryReview: String = cursor.getString(3)
                val username: String = cursor.getString(4)
                val reviewId: Int = cursor.getInt(5)
                val userid: Int = cursor.getInt(6)

                val review = Review(
                    username, title, description, rating, countryReview,
                    byteArrayOf(), reviewId, userid
                )

                reviewList.add(review)
            } while (cursor.moveToNext())

        cursor.close()
        db.close()

        return reviewList
    }


    fun getFutureLocation(tId: Int): ArrayList<UserMap>  {
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $futureInterestTableName WHERE $columnUserId == $tId"

        val cursor: Cursor = db.rawQuery(sqlStatement, null)
        var futurelocal = ArrayList<Future_location>()
        val userM = UserMap("mom",futurelocal)
        var userMList = ArrayList<UserMap>()

        if (cursor.moveToFirst()) {
            do {
                val name: String = cursor.getString(0)
                val country: String = cursor.getString(1)
                val cityTown: String = cursor.getString(2)
                val desc: String = cursor.getString(3)
                val booked: String = cursor.getString(4)
                val userMTitle: String = cursor.getString(5)
                val id: Int = cursor.getInt(6)
                val userid: Int = cursor.getInt(7)
                val latitude: Double = cursor.getDouble(8)
                val longitude: Double = cursor.getDouble(9)
                val f = Future_location(name,country,cityTown,desc, booked,id,userid,latitude,longitude)
                futurelocal.add(f)
                val userM = UserMap(userMTitle,futurelocal)
                userMList.add(userM)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return userMList
    }

    fun getUserMapTitle(tId: Int): ArrayList<UserMap>  {
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT $columnUserMapTitle, $columnFutureLocationId FROM $futureInterestTableName WHERE $columnUserId == $tId"

        val cursor: Cursor = db.rawQuery(sqlStatement, null)
        var futurelocal = ArrayList<Future_location>()
        val userM = UserMap("mom",futurelocal)
        var userMList = ArrayList<UserMap>()

        if (cursor.moveToFirst()) {
            do {
                var userMTitle: String = cursor.getString(0)
                val id: Int = cursor.getInt(1)

                val f = Future_location("name","country","cityTown","desc", "false",id,tId,012.3123,14.1241)
                futurelocal.add(f)
                val userM = UserMap(userMTitle,futurelocal)
                userMList.add(userM)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return userMList
    }

    /**
     * This will collect a specific password based on the Id passed and return every column within that row
     */
    fun getPassword(pId: Int): Password {
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $passwordTableName WHERE $column_PasswordId = $pId"

        val cursor: Cursor = db.rawQuery(sqlStatement, null)
        if  (cursor.moveToFirst()) {
            db.close()

            return Password(cursor.getInt(0),
                cursor.getString(1),
                cursor.getInt(2))
        } else {
            db.close()
            return Password(0,"Cannot find password", 0)
        }
    }

    /**
     * This will reorganise the tourist table and collect the most recently added row.
     */
    fun getLastAddedTourist(): Tourist {
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $touristTableName WHERE $column_UserID = (SELECT MAX($column_UserID) FROM $touristTableName)"

        val cursor: Cursor = db.rawQuery(sqlStatement, null)
        if  (cursor.moveToFirst()){
            db.close()

            return Tourist(cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4))
        } else{
            db.close()
            return Tourist (0, "Username not found", "firstname not found",
                "lastname not found", "email not found")

        }
    }

    /**
     * updates a specific row in the tourist table
     */
    fun updateTourist(tourist: Tourist): Boolean {
        val db: SQLiteDatabase = this.writableDatabase
        val cv: ContentValues = ContentValues()

        cv.put(column_Firstname, tourist.firstname)
        cv.put(column_Lastname, tourist.lastname)
        cv.put(column_Username, tourist.username)
        cv.put(column_Email, tourist.email)

        val result = db.update(touristTableName,cv,"$column_UserID = ${tourist.userID}", null) == 1
        db.close()
        return result
    }

    /**
     * Updates a specific row in the review table
     *
     */

    fun updateReview(review: Review): Boolean {
        val db: SQLiteDatabase = this.writableDatabase
        val cv: ContentValues = ContentValues()

        cv.put(columnReviewTitle,review.title)
        cv.put(columnDescriptions,review.description)
        cv.put(columnReviewCountry,review.countryReview)
        cv.put(columnReviewRating,review.rating)
        cv.put(columnReviewImage,review.image)
        cv.put(columnReviewUsername,review.username)
        cv.put(columnReviewUserId,review.username)

        val result = db.update(reviewTableName,cv,"$columnReviewId = ${review.reviewId}", null) == 1
        db.close()
        return result
    }


    /**
     * this will specifically collect every single rows email column and return it as an arraylist.
     */
    fun getEmail(): ArrayList<String>{

        val emails = ArrayList<String>()
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = " SELECT $column_Email FROM $touristTableName "

        val cursor: Cursor = db.rawQuery(sqlStatement, null)

        if (cursor.moveToFirst())
            do{
                emails.add(cursor.getString(4))
            } while (cursor.moveToFirst())

        cursor.close()
        db.close()

        return emails
    }
}