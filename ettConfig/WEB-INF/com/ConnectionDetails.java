package com;
import java.util.*;
import java.sql.*;
public class ConnectionDetails
{
        public String CONNECTION_NAME = null;
        public String CONNECTION_URL = null;
        public String CONNECTION_TPS = null;
        public String SENDER_ID = null;
        public String CONNECTION_PREFIX = null;
        public String CONNECTION_TYPE = null;
        public String CONNECTION_IP = null;
        public String CONNECTION_PORT = null;
        public String CONNECTION_USERNAME = null;
        public String CONNECTION_PASSWORD = null;

        public ConnectionDetails(String a,String b,String c,String d)
        {
                CONNECTION_NAME = a;
                CONNECTION_URL = b;
                CONNECTION_TPS = c;
                CONNECTION_PREFIX  = d;
        }
}

