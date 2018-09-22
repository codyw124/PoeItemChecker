using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading;
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
        public MainWindow(String accountName)
        {
            InitializeComponent();

            //lookup the current change id
            String currentNextChangeId = getCurrentChangeId();

            //then start parsing pages
            while (true)
            {
                currentNextChangeId = parsePageAtUrl(currentNextChangeId, accountName);
            }
        }

        private String parsePageAtUrl(String currentNextChangeId, String accountName)
        {
            // the url we will use to start with
            String url = "http://www.pathofexile.com/api/public-stash-tabs?id=" + currentNextChangeId;

            // get its contents
            String currentPageJson = new WebClient().DownloadString(url);

            //make a stopwatch
            Stopwatch stopWatch = new Stopwatch();

            //start it
            stopWatch.Start();

            //deserialize the json
            dynamic currentPageObject = JsonConvert.DeserializeObject(currentPageJson);

            //get the next change id property from that
            String nextChangeId = currentPageObject.next_change_id;

            dynamic[] stashes = jArrayToArray(currentPageObject.stashes);

            // for each stash
            foreach (dynamic currentStash in stashes)
            {
                // print the owners name
                String currentAccountName = currentStash.accountName;

                // if the owner isnt null and they are who we are looking for
                if (currentAccountName != null && currentAccountName == accountName)
                {
                    // get the array of items in this stash
                    dynamic[] itemsInCurrentStash = jArrayToArray(currentStash.items);

                    // for every item
                    foreach (dynamic item in itemsInCurrentStash)
                    {
                        // get the current item name
                        String currentItemName = item.name;

                        // if it has a name
                        if (currentItemName != null)
                        {
                            if (currentItemName != "")
                            {
                                String[] test = currentItemName.Split('>');

                                // print it
                                Console.WriteLine(test[test.Length - 1]);
                            }
                        }
                    }
                }
            }

            //stop the stopwatch
            stopWatch.Stop();

            //sleep if needed
            sleepIfNeeded(stopWatch);

            return nextChangeId;
        }

        private static void sleepIfNeeded(Stopwatch stopWatch)
        {
            //get how long has passed
            TimeSpan ts = stopWatch.Elapsed;

            //get it in milliseconds
            int millisSinceStart = ts.Milliseconds;

            //this is how long we must wait between api calls
            const int TOTAL_TIME_TO_WAIT = 750;

            //if it hasnt been enough time to call api again 
            if (millisSinceStart < TOTAL_TIME_TO_WAIT)
            {
                //wait how ever long we need to
                Thread.Sleep(TOTAL_TIME_TO_WAIT - millisSinceStart);
            }
        }

        private dynamic[] jArrayToArray(JArray theJArray)
        {
            //make an array for the stashes
            dynamic[] stashes = new dynamic[theJArray.Count];

            //add the jarray stuff to the regular array
            int i = 0;
            foreach (dynamic item in theJArray)
            {
                stashes[i++] = item;
            }

            return stashes;
        }

        private void button(object sender, RoutedEventArgs e)
        {
        }

        private void addTab(String newTabName)
        {
            //make a new tab item
            TabItem newTab = new TabItem();

            //set its header
            newTab.Header = newTabName;

            //add it to the tabs on the left of the screen
            tabs.Items.Add(newTab);
        }

        private String getCurrentChangeId()
        {
            //this is the url
            String url = "http://poe.ninja/api/Data/GetStats/";

            //download the string from that url
            String json = new WebClient().DownloadString(url);

            //deserialize the json
            dynamic jsonAsObject = JsonConvert.DeserializeObject(json);

            //get the next change id property from that
            String nextChangeId = jsonAsObject.next_change_id;

            return nextChangeId;
        }
    }
}