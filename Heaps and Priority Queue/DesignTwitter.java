/**
 * Twitter System Design - Machine Coding
 * TC: O(klogk) -> follow(), unfollow(), postTweet() -> O(1);
 * getNewsFeed() -> It should be O(K * M_limit * log(K * M_limit) + N_feed * log(K * M_limit)) where K is number of followees, M_limit is 10 (tweets per user added to PQ), N_feed is 10 (news feed size). This simplifies to O(K log K) if we treat 10 as a constant.
 * SC: O(NumUsers + TotalTweets + TotalFollowRelationships). Temporary space for getNewsFeed is O(MaxFolloweesOfOneUser)
 * 
 * 1. We need to store the list of tweets posted by any user. This can be stored in a list or a hash set. But, we need to make sure the tweets are ordered on the timestamp. So, we insert the latest tweet at the beginning. Hence, we use `LinkedList` and insert at `head` everytime.
 * 2. Every users follows people (followee) and is followed by some people (followers). Hence, we need to store all the users globally. So, a map for faster look-up.
 * 3. The time at which a tweet is posted has to be maintained globally.
 * 4. Hence, we need a User class to model user - `userId`, list of `tweets` and list of `followees` (the people user is following).
 * 5. constructor -> initialize the private members.
 * 6. postTweet() -> if the user doesn't exist yet, create one and post tweet with global timestamp.
 * 7. follow() -> if the `followee` or the `follower` doesn't exist, create them. Then, the `follower` follows the `followee`.
 * 8. unfollow() -> if the `followee` is not present in the `followees list` of the follower -> return as not possible to unfollow. Else, `follower` unfollows the `followee`.
 * 9. getNewsFeed() -> get self tweets (only 10) and get tweets from followees (the people user follows), again, only 10. Add all these to a Priority Queue which is sorted in desc. based on time.
 * Then, select only top 10 and add.
 * Selecting only 10 from self and from followees makes the execution faster.
 *
 */
class Twitter {
    // Tweet object has time and tweetId
    private class Tweet {
        private int time;
        private int tweetId;

        Tweet(int time, int tweetId) {
            this.time = time;
            this.tweetId = tweetId;
        }
    }

    // User has userId, its tweets, and its followees, i.e, the users which the currentUser is following on Twitter. So, user is a follower and the `followees` are the users they are following.
    private class User {
        private int userId;
        private List<Tweet> tweets;
        private Set<Integer> followees;

        User(int userId) {
            this.userId = userId;
            this.tweets = new LinkedList<>();
            this.followees = new HashSet<>();
        }

        public void addTweet(Tweet tweet) {
            this.tweets.addFirst(tweet);
        }

        public void addFollowee(int id) {
            this.followees.add(id);
        }

        public void removeFollowee(int id) {
            this.followees.remove(id);
        }
    }

    private Map<Integer, User> userMap;
    private int globalTime;

    public Twitter() {
        userMap = new HashMap<>();
        globalTime = 0;
    }

    public void postTweet(int userId, int tweetId) {
        globalTime++;

        if (!userMap.containsKey(userId)) {
            userMap.put(userId, new User(userId));
        }
        User user = userMap.get(userId);
        Tweet tweet = new Tweet(globalTime, tweetId);

        user.addTweet(tweet);
    }

    public List<Integer> getNewsFeed(int userId) {
        if (!userMap.containsKey(userId)) {
            return new ArrayList<>();
        }

        // max heap
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> b.time - a.time);
        // top 10 tweets of user and it's followees.
        User user = userMap.get(userId);

        // User user is already fetched and known to exist at this point
        // Add user's own tweets
        List<Tweet> selfTweets = user.tweets;
        for (int i = 0; i < selfTweets.size() && i < 10; i++) {
            pq.offer(selfTweets.get(i));
        }

        // Add tweets from followees
        for (int followeeId : user.followees) {
            User followee = userMap.get(followeeId);
            if (followee != null) {
                // ensure followees exist
                List<Tweet> followeeTweets = followee.tweets;
                for (int i = 0; i < followeeTweets.size() && i < 10; i++) {
                    pq.offer(followeeTweets.get(i));
                }
            }
        }

        // first 10 tweets
        int count = 0;
        List<Integer> res = new ArrayList<>();
        while (!pq.isEmpty() && count < 10) {
            Tweet tweet = pq.poll();
            res.add(tweet.tweetId);
            count++;
        }
        return res;
    }

    public void follow(int followerId, int followeeId) {
        if (followerId == followeeId) {
            return; // User cannot follow self
        }

        // Ensure the follower exists
        if (!userMap.containsKey(followerId)) {
            userMap.put(followerId, new User(followerId));
        }

        // Ensure the followee exists
        if (!userMap.containsKey(followeeId)) {
            userMap.put(followeeId, new User(followeeId));
        }

        userMap.get(followerId).addFollowee(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        User user = userMap.get(followerId);
        if (user == null) {
            return;
        } 

        if(!user.followees.contains(followeeId)) {
            return; 
        }

        // userMap.get(followerId).removeFollowee(followeeId);
        user.removeFollowee(followeeId);
    }
}

/**
 * Your Twitter object will be instantiated and called as such:
 * Twitter obj = new Twitter();
 * obj.postTweet(userId,tweetId);
 * List<Integer> param_2 = obj.getNewsFeed(userId);
 * obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */
