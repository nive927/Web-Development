EE = require('events').EventEmitter;
ee = new EE();
die = false;
ee.on('die', function() {
die = true;
});
setTimeout(function() {
ee.emit('die');
}, 2000);
//while(!die) {
//}
console.log('done');