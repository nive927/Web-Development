var fs = require("fs");

fs.readFile('D:/work/nive/SSN-College-Of-Engineering/all-SEMESTERS/SEM6/UCS1611 Internet Programming Lab/d7-node/NodeJS/input.txt', function (err, data) {
   if (err) return console.error(err);
   console.log(data.toString());
});

console.log("Program Ended");