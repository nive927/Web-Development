const fs = require('fs')

// Creates /tmp/a/apple, regardless of whether `/tmp` and /tmp/a exist.
fs.mkdir('./a/apple', { recursive: true }, (err) => {
    if (err) throw err;
  });