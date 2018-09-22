using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace test
{
    public class Program
    {
        private class Item
        {
            private String itemName_;
            private List<String> mods_;

            public String getName()
            {
                return itemName_;
            }

            public List<String> getMods()
            {
                return mods_;
            }
        }

        private Dictionary<String, List<Item>> stashNameToListOfItems_;
        private String currentChangeId_;
        private String[] goodAttributes_;

        public Program()
        {
            stashNameToListOfItems_ = new Dictionary<string, List<Item>>();
            currentChangeId_ = getCurrentChangeId();

            goodAttributes_ = new String[]{"fire resistance", "lightning resistance", "cold resistance", "maximum life"};

        }

        static void Main(string[] args)
        {
            Program test = new Program();

            //continuously parse pages
            while (true)
            {
                //get the next id to scan
                test.parsePageAtUrl("paridox125");

                Console.Clear();

                test.printStashes();
            }
        }

        private void printStashes()
        {
            //for each stash
            foreach (String stashName in stashNameToListOfItems_.Keys)
            {
                //print its name
                Console.WriteLine(stashName);

                //for each item in that stash
                foreach (Item item in stashNameToListOfItems_[stashName])
                {
                    //print a tab followed by the item name
                    Console.WriteLine('\t' + item.getName() + '\t' + isGood(item));
                }
            }
        }

        private string isGood(Item item)
        {
            throw new NotImplementedException();
        }

        private void parsePageAtUrl(String accountName)
        {
            // the url we pull from
            String url = "http://www.pathofexile.com/api/public-stash-tabs?id=" + currentChangeId_;

            // get its contents
            String currentPageJson = new WebClient().DownloadString(url);

            //make a stopwatch
            Stopwatch stopWatch = new Stopwatch();

            //start it
            stopWatch.Start();

            //deserialize the json
            dynamic currentPageObject = JsonConvert.DeserializeObject(currentPageJson);

            //convert the JArray of stashes to a usable array of stashes
            dynamic[] stashes = jArrayToArray(currentPageObject.stashes);

            // for each stash
            foreach (dynamic currentStash in stashes)
            {                
                // if the owner of that stash isnt null and they are who we are looking for
                if (currentStash.accountName != null && currentStash.accountName.ToString().ToUpper() == accountName.ToUpper())
                {
                    //if the list of stashes we have exists
                    if (stashNameToListOfItems_.ContainsKey(currentStash.stash.ToString()))
                    {
                        //delete it because we are adding the updated version
                        stashNameToListOfItems_.Remove(currentStash.stash.ToString());
                    }

                    //make a list to hold the names of the items in this stash
                    List<Item> itemsInThisStash = new List<Item>();

                    // get the array of items in this stash
                    dynamic[] itemsInCurrentStash = jArrayToArray(currentStash.items);

                    // for every item
                    foreach (dynamic item in itemsInCurrentStash)
                    {
                        // get the current item name
                        String currentItemName = item.name;

                        //get the 

                        // if it has a name
                        if (currentItemName != null && currentItemName != "")
                        {
                            String[] test = currentItemName.Split('>');

                            //add it to the list of items
                            itemsInThisStash.Add(test[test.Length - 1]);
                        }
                    }

                    //add the list of items in that stash to our map with the key of that stashes name
                    stashNameToListOfItems_.Add(currentStash.stash.ToString(), itemsInThisStash);
                }
            }

            //stop the stopwatch
            stopWatch.Stop();

            //sleep if needed
            sleepIfNeeded(stopWatch);

            //update the current change id
            currentChangeId_ = currentPageObject.next_change_id;
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