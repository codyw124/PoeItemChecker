using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace Stash__
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();

            String currentNextChangeId = getCurrentChangeId();
        }

        private void button(object sender, RoutedEventArgs e)
        {
            
        }

        private void addTab(String newTabName)
        {
            TabItem newTab = new TabItem();

            newTab.Header = newTabName;

            tabs.Items.Add(newTab);
        }

        private String getJsonFromUrl(String url)
        {
            return new WebClient().DownloadString(url);
        }

        private String getCurrentChangeId()
        {
            String url = "http://poe.ninja/api/Data/GetStats/";
            String json = getJsonFromUrl(url);

            JsonTextReader reader = new JsonTextReader(new StringReader(json));

            bool keepLooking = true;

            String nextChangeId = "";

            do
            {
                reader.Read();

                if(reader.Value != null && reader.Value.ToString() == "next_change_id")
                {
                    keepLooking = false;
                    reader.Read();
                    nextChangeId = reader.Value.ToString(); 
                }
            } while(keepLooking);

            return nextChangeId;
        }
    }
}