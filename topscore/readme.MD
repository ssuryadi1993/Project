How to run application:
1. gradlew bootRun in terminal
2. run /topscore/build/libs/topscore-0.0.1-SNAPSHOT.jar

How to run unit test :
run from terminal : ./gradlew test

getPlayerHistory(String name)
//name is case insensitive
curl --location --request GET 'localhost:8080/playerHistory/stephen'

getAllScores()
//display all scores without paging
curl --location --request GET 'localhost:8080/scores'

getScoresByFilter()
//page = pageIndex
//size = dataSize in 1 page
//name = searched names, could search more than 1 name using "_", name is case insensitive
//1. search by name
curl --location --request GET 'localhost:8080/scores/filter?name=kevin_STEPHEN&page=0&size=1'
//after = timestamp using date format YYMMDDHHMMSS
//before = timestamp using date format YYMMDDHHMMSS
//between = if true then using operator AND, if alse then using operator OR
//2. search by time created (after)
curl --location --request GET 'localhost:8080/scores/filter?after=1&page=0&size=10'
//3. search by time created (before)
curl --location --request GET 'localhost:8080/scores/filter?before=999999999999&page=0&size=10'
//4. search by time created (between, after < x AND  x < before)
curl --location --request GET 'localhost:8080/scores/filter?before=201209235959&after=123&between=true&page=0&size=10'
//5. search by time created (except, after < x OR x < before
curl --location --request GET 'localhost:8080/scores/filter?before=201209235959&after=123&between=false&page=0&size=10'


getScoreById(Long id)
curl --location --request GET 'localhost:8080/scores/3'


newTopSore(Score score)
curl --location --request POST 'localhost:8080/scores' \
--header 'Content-type: application/json' \
--data-raw '{"playerName" : "STEPHEN", "score" : 0, "createdTime" : 1608446336800}'
//date format YYMMDDHHMMSS

deleteTopScore(Long id)
curl --location --request DELETE 'localhost:8080/scores/5'
//5 is parameter