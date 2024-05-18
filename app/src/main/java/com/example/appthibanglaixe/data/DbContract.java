package com.example.appthibanglaixe.data;

import android.provider.BaseColumns;

public class DbContract {
    public static final class DESEntry implements BaseColumns {
        public static final String DESTABLE = "destable";
        public static final String COLUMN_ID_BO = "idBo";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_PUBLICATION_DATE = "publicationDate";
        public static final String COLUMN_CONTENT = "content";
    }

    public static class ComicsEntry implements BaseColumns {
        public static final String TABLE_NAME_COMICS = "comics";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_DESCRIPTION = "description";
    }

    public static final class LoginEntry implements  BaseColumns {
        public static final String TABLE_NAME_LOGIN = "login";
        public static final String _ID = "id";
        public static final String COLUMN_USER = "username";
        public static final String COLUMN_PASS = "password";
    }
}
