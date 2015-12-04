namespace OhmBaseOpenWei{
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
    public class tableinfo
    {
        private string name;
        private string notes;
        private string quantity;
        private string last_modified;
        private string spec_sheets;
        
        public string Name
        {
            get
            {
                return name;
            }

            set
            {
                name = value;
            }
        }
        public string Notes
        {
            get
            {
                return notes;
            }

            set
            {
                notes = value;
            }
        }
        public string Quantity
        {
            get
            {
                return quantity;
            }

            set
            {
                quantity = value;
            }
        }
        public string Last_Modified
        {
            get
            {
                return last_modified;
            }

            set
            {
                last_modified = value;
            }
        }
        public string Spec_Sheets
        {
            get
            {
                return spec_sheets;
            }

            set
            {
                spec_sheets = value;
            }
        }
    }
}