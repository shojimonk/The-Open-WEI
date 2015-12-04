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

         List<tableinfo> list_table = new List<tableinfo>();
         NpgsqlConnection conn = new NpgsqlConnection("Server=Localhost; Port=5432; Database=ohmbaseopenwei; User Id=postgres; Password=1234567890;");
         conn.Open();
         NpgsqlCommand cmd;
         NpgsqlDataReader reader;
         if (name == "" || name == " "){
            cmd = new NpgsqlCommand("SELECT name, notes, quantity, last_modified, spec_sheets FROM " + part, conn);
           reader = cmd.ExecuteReader();
             while (reader.Read())
            {
                 list_table.Add(new tableinfo { Name = reader[0].ToString(), Notes = reader[1].ToString(), Quantity = reader[2].ToString(), Last_Modified = reader[3].ToString().Remove(reader[3].ToString().IndexOf(' ')), Spec_Sheets = reader[4].ToString() });
            }
        }
         else{
            cmd = new NpgsqlCommand("SELECT name, notes, quantity, last_modified, spec_sheets FROM "+part+" WHERE name LIKE :namep", conn);
            cmd.Parameters.Add(new NpgsqlParameter("namep", NpgsqlDbType.Varchar));
            string nameset = "%" + name + "%";
            cmd.Parameters[0].Value = nameset;
            reader = cmd.ExecuteReader();
            while (reader.Read())
            {
                 list_table.Add(new tableinfo { Name = reader[0].ToString(), Notes = reader[1].ToString(), Quantity = reader[2].ToString(), Last_Modified = reader[3].ToString().Remove(reader[3].ToString().IndexOf(' ')), Spec_Sheets = reader[4].ToString() });
            }
            conn.Close();
            conn.Open();
            char[] delimiterChars = { ' ', ',', '.', ':',';'};

            string[] split = name.Split(delimiterChars);
             List<string> splitnames =new List<string>(split);
             splitnames.Remove("");
             splitnames.Remove(null);
            string andquery = "";
            string orquery = "";
             bool firstiteration = true;
             for (int i = 0; i < splitnames.Count; i++)
             {
                 if (firstiteration == true)
                 {
                     andquery = andquery + ":name" + i.ToString() ;
                     orquery = orquery + ":name" + i.ToString();
                     firstiteration = false;
                   
                 }
                 else
                 {
                     andquery = andquery +" AND name LIKE"+ " :name" + i.ToString();
                     orquery = orquery + " OR name LIKE" + " :name" + i.ToString();
                     
                 }

             }
             cmd = new NpgsqlCommand("SELECT name, notes, quantity, last_modified, spec_sheets FROM " + part + " WHERE name LIKE " + andquery, conn);
             for (int i = 0; i < splitnames.Count; i++)
             {
                 cmd.Parameters.Add(new NpgsqlParameter("name"+i.ToString(), NpgsqlDbType.Varchar));
                 nameset = "%" + splitnames[i] + "%";
                 cmd.Parameters[i].Value = nameset;                 
             }
             reader = cmd.ExecuteReader();
             while (reader.Read())
             {
                 bool alreadyExists = list_table.Any(x => x.Name == reader[0].ToString() && x.Notes == reader[1].ToString() && x.Quantity == reader[2].ToString() && x.Last_Modified == reader[3].ToString().Remove(reader[3].ToString().IndexOf(' ')) && x.Spec_Sheets == reader[4].ToString());
                 if (alreadyExists == false)
                 {
                     list_table.Add(new tableinfo { Name = reader[0].ToString(), Notes = reader[1].ToString(), Quantity = reader[2].ToString(), Last_Modified = reader[3].ToString().Remove(reader[3].ToString().IndexOf(' ')), Spec_Sheets = reader[4].ToString() });
                 }
             }
             conn.Close();
             conn.Open();
              
             cmd = new NpgsqlCommand("SELECT name, notes, quantity, last_modified, spec_sheets FROM " + part + " WHERE name LIKE " + orquery, conn);
             for (int i = 0; i < splitnames.Count; i++)
             {
                 cmd.Parameters.Add(new NpgsqlParameter("name" + i.ToString(), NpgsqlDbType.Varchar));
                 nameset = "%" + splitnames[i] + "%";
                 cmd.Parameters[i].Value = nameset;
             }
             reader = cmd.ExecuteReader();
             while (reader.Read())
             {
                 bool alreadyExists = list_table.Any(x => x.Name == reader[0].ToString() && x.Notes == reader[1].ToString() && x.Quantity == reader[2].ToString() && x.Last_Modified == reader[3].ToString().Remove(reader[3].ToString().IndexOf(' ')) && x.Spec_Sheets == reader[4].ToString());
                 if (alreadyExists == false)
                 {
                     list_table.Add(new tableinfo { Name = reader[0].ToString(), Notes = reader[1].ToString(), Quantity = reader[2].ToString(), Last_Modified = reader[3].ToString().Remove(reader[3].ToString().IndexOf(' ')), Spec_Sheets = reader[4].ToString() });
                 }
             }

         }
               
               
                   conn.Close();
               
           
            return list_table;
        }   
    }
}