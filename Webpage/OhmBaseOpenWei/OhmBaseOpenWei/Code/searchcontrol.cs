namespace OhmBaseOpenWei
{

    using System;
    using System.Collections.Generic;
    using System.Data;
    using System.Linq;
    using System.Web;
    using Npgsql;
    using NpgsqlTypes;   

    public class searchcontrol
    {
        public static List<tableinfo> Read(string part,string name)
        {
         // have table for all search result to enter
         List<tableinfo> list_table = new List<tableinfo>();
        // connection information for the database
         NpgsqlConnection conn = new NpgsqlConnection("Server=Localhost; Port=5432; Database=ohmbaseopenwei; User Id=postgres; Password=;");
         //connect to database
         conn.Open();
         NpgsqlCommand cmd;
         NpgsqlDataReader reader;
        //cheks if the user entered any name info if not then it just returns the whole table
         if (name == "" || name == " "){
            cmd = new NpgsqlCommand("SELECT name, notes, quantity, last_modified, spec_sheets FROM " + part, conn);
           reader = cmd.ExecuteReader();
            //loads the results into the table  
            while (reader.Read())
            {
                 list_table.Add(new tableinfo { Name = reader[0].ToString(), Notes = reader[1].ToString(), Quantity = reader[2].ToString(), Last_Modified = reader[3].ToString().Remove(reader[3].ToString().IndexOf(' ')), Spec_Sheets = reader[4].ToString() });
            }
        }
         else{
            //first query which checks uses whole entered data as the parameters for the search
            cmd = new NpgsqlCommand("SELECT name, notes, quantity, last_modified, spec_sheets FROM "+part+" WHERE name ILIKE :namep", conn);
            //prevents injection attacks
            cmd.Parameters.Add(new NpgsqlParameter("namep", NpgsqlDbType.Varchar));
            string nameset = "%" + name + "%";
            cmd.Parameters[0].Value = nameset;
            reader = cmd.ExecuteReader();
            //loads results into table
            while (reader.Read())
            {
                 list_table.Add(new tableinfo { Name = reader[0].ToString(), Notes = reader[1].ToString(), Quantity = reader[2].ToString(), Last_Modified = reader[3].ToString().Remove(reader[3].ToString().IndexOf(' ')), Spec_Sheets = reader[4].ToString() });
            }
            conn.Close();
            conn.Open();
            //the entered name info is then split into mutilple strings by these delimeters
            char[] delimiterChars = { ' ', ',', '.', ':',';'};
            string[] split = name.Split(delimiterChars);
             List<string> splitnames =new List<string>(split);
             //removes unwanted string values from split strings
             splitnames.Remove("");
             splitnames.Remove(null);
            string andquery = "";
            string orquery = "";
             bool firstiteration = true;
             //new set of of strings are then put into AND and OR queries
             for (int i = 0; i < splitnames.Count; i++)
             {
                 if (firstiteration == true)
                 {
                     //sets up the new AND and OR queries
                     andquery = andquery + ":name" + i.ToString() ;
                     orquery = orquery + ":name" + i.ToString();
                     firstiteration = false;
                   
                 }
                 else
                 {
                     //adds to new AND and OR queries
                     andquery = andquery +" AND name ILIKE"+ " :name" + i.ToString();
                     orquery = orquery + " OR name ILIKE" + " :name" + i.ToString();
                     
                 }

             }
             //Searches with new AND query
             cmd = new NpgsqlCommand("SELECT name, notes, quantity, last_modified, spec_sheets FROM " + part + " WHERE name ILIKE " + andquery, conn);
             for (int i = 0; i < splitnames.Count; i++)
             {
                 cmd.Parameters.Add(new NpgsqlParameter("name"+i.ToString(), NpgsqlDbType.Varchar));
                 nameset = "%" + splitnames[i] + "%";
                 cmd.Parameters[i].Value = nameset;                 
             }
             reader = cmd.ExecuteReader();
             while (reader.Read())
             {
                 //checks if the data that was returned was already returned by the previous query and rejects it if it was
                 bool alreadyExists = list_table.Any(x => x.Name == reader[0].ToString() && x.Notes == reader[1].ToString() && x.Quantity == reader[2].ToString() && x.Last_Modified == reader[3].ToString().Remove(reader[3].ToString().IndexOf(' ')) && x.Spec_Sheets == reader[4].ToString());
                 if (alreadyExists == false)
                 {
                     list_table.Add(new tableinfo { Name = reader[0].ToString(), Notes = reader[1].ToString(), Quantity = reader[2].ToString(), Last_Modified = reader[3].ToString().Remove(reader[3].ToString().IndexOf(' ')), Spec_Sheets = reader[4].ToString() });
                 }
             }
             conn.Close();
             conn.Open();
             //Searches with new OR query 
             cmd = new NpgsqlCommand("SELECT name, notes, quantity, last_modified, spec_sheets FROM " + part + " WHERE name ILIKE " + orquery, conn);
             for (int i = 0; i < splitnames.Count; i++)
             {
                 cmd.Parameters.Add(new NpgsqlParameter("name" + i.ToString(), NpgsqlDbType.Varchar));
                 nameset = "%" + splitnames[i] + "%";
                 cmd.Parameters[i].Value = nameset;
             }
             reader = cmd.ExecuteReader();
             while (reader.Read())
             {
                 //checks if the data that was returned was already returned by the previous query and rejects it if it was
                 bool alreadyExists = list_table.Any(x => x.Name == reader[0].ToString() && x.Notes == reader[1].ToString() && x.Quantity == reader[2].ToString() && x.Last_Modified == reader[3].ToString().Remove(reader[3].ToString().IndexOf(' ')) && x.Spec_Sheets == reader[4].ToString());
                 if (alreadyExists == false)
                 {
                     list_table.Add(new tableinfo { Name = reader[0].ToString(), Notes = reader[1].ToString(), Quantity = reader[2].ToString(), Last_Modified = reader[3].ToString().Remove(reader[3].ToString().IndexOf(' ')), Spec_Sheets = reader[4].ToString() });
                 }
             }

         }
               
               
                   conn.Close();
               
           //returns the results of the search
            return list_table;
        }   
    }
}