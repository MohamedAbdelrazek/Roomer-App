package mohamedabdelrazek.com.roomer.GuestsData;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import static mohamedabdelrazek.com.roomer.GuestsData.GuestsContract.GuestsEntry.COLUMN_GUEST_AGE;
import static mohamedabdelrazek.com.roomer.GuestsData.GuestsContract.GuestsEntry.COLUMN_GUEST_MOBILE_NUMBER;
import static mohamedabdelrazek.com.roomer.GuestsData.GuestsContract.GuestsEntry.COLUMN_GUEST_NAME;
import static mohamedabdelrazek.com.roomer.GuestsData.GuestsContract.GuestsEntry.CONTENT_AUTHORITY;
import static mohamedabdelrazek.com.roomer.GuestsData.GuestsContract.GuestsEntry.CONTENT_ITEM_TYPE;
import static mohamedabdelrazek.com.roomer.GuestsData.GuestsContract.GuestsEntry.CONTENT_LIST_TYPE;
import static mohamedabdelrazek.com.roomer.GuestsData.GuestsContract.GuestsEntry.NATIONNAL_ID;
import static mohamedabdelrazek.com.roomer.GuestsData.GuestsContract.GuestsEntry.PATH_ROMER;
import static mohamedabdelrazek.com.roomer.GuestsData.GuestsContract.GuestsEntry.TABLE_NAME;
import static mohamedabdelrazek.com.roomer.GuestsData.GuestsContract.GuestsEntry._ID;

/**
 * Created by Mohamed on 9/19/2016.
 */
public class GusetProvidor extends ContentProvider {
    private GuestDbHelper dbHelper;
    private static final int ROMER = 300;
    private static final int ROMER_ID = 301;
    private static final UriMatcher zUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        zUriMatcher.addURI(CONTENT_AUTHORITY, PATH_ROMER, ROMER);
        zUriMatcher.addURI(CONTENT_AUTHORITY, PATH_ROMER + "/#", ROMER_ID);
    }


    @Override
    public boolean onCreate() {
        dbHelper = new GuestDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor zCursor;
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        int match = zUriMatcher.match(uri);
        switch (match) {
            case ROMER:
                zCursor = database.query(TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case ROMER_ID:
                selection = _ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                zCursor = database.query(TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalStateException("cant Query this URI ! ");

        }
        // Set notification URI on the Cursor,
        // so we know what content URI the Cursor was created for.
        // If the data at this URI changes, then we know we need to update the Cursor.
        zCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return zCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = zUriMatcher.match(uri);
        switch (match) {
            case ROMER:
                return CONTENT_LIST_TYPE;
            case ROMER_ID:
                return CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }


    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) throws IllegalArgumentException {
        int match = zUriMatcher.match(uri);
        switch (match) {
            case ROMER:  // DEAL ONLY WITH THE ENTIRE TABLE.
                return insertGuest(uri, contentValues);
            default:
                throw new IllegalArgumentException("cant handle this type of URI ! ");

        }
    }


    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        final int match = zUriMatcher.match(uri);
        switch (match) {
            case ROMER:
                return updateGuest(uri, contentValues, selection, selectionArgs);
            case ROMER_ID:
                // For the PET_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = _ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateGuest(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        // Track the number of rows that were deleted
        int rowsDeleted;

        final int match = zUriMatcher.match(uri);

        switch (match) {
            case ROMER:
                // Delete all rows that match the selection and selection args
                rowsDeleted = database.delete(TABLE_NAME, selection, selectionArgs);
                break;
            case ROMER_ID:
                // Delete a single row given by the ID in the URI
                selection = _ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);

        }
        if (rowsDeleted!=0)
        {
        getContext().getContentResolver().notifyChange(uri , null);}

        return rowsDeleted;

    }

    private int updateGuest(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        int rowsUpdated = database.update(TABLE_NAME, contentValues, selection, selectionArgs);
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }


    private Uri insertGuest(Uri uri, ContentValues contentValues) {

        String name = contentValues.getAsString(COLUMN_GUEST_NAME);
        if (!name.contains(" ")) {
            //the user should enter at least first and  Last  name ...
            throw new IllegalArgumentException("Plz specify the full name ! ");
        }
        String nationnal_id = contentValues.getAsString(NATIONNAL_ID);
        if (nationnal_id.length()<1)
        {
            throw new IllegalArgumentException("The national id must be 14 Number !");
        }
        String mobile_number=contentValues.getAsString(COLUMN_GUEST_MOBILE_NUMBER);
        if (mobile_number.length()<1)
        {

            throw new IllegalArgumentException("This Mobile is incorrect !");

        }

        String  age= contentValues.getAsString(COLUMN_GUEST_AGE);
        if (TextUtils.isEmpty(age))
        {
            throw new  IllegalArgumentException("plz specify th age !");
        }
        else if (Integer.parseInt(age)<14)
        {
            throw  new IllegalArgumentException("Sorry you are so  Young :(  !" );
        }

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        long id = database.insertOrThrow(TABLE_NAME, null, contentValues);

        if (id == -1) {
            return null;
        }

        // Notify all listeners that the data has changed for the pet content URI
        getContext().getContentResolver().notifyChange(uri, null);
        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(uri, id);
    }

}
