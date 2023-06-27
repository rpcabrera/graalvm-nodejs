var timesForGreeting = 10;
var greeting = (nTimes) => {
    var list = [];
    for(var i = 0; i<nTimes; i++)
        list.push("Hello from sample.js file!");
    return list;
}


var greetings = greeting(timesForGreeting).join(',');
