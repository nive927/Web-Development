const franc = require('franc');
const colors = require('colors');
const langs = require('langs');

// make sure to use double quotes for arguments in Windows
const inputArg = process.argv[2];
// console.log(inputArg)

const langCode = franc(inputArg);

try {
    const language = langs.where("3", langCode);
    // console.log(language)
    console.log(`Our best guess is: ${language.name.green}`);
}

catch(e) {
    console.log("SORRY, couldn't figure it out!\nTry with MORE TEXT! ".red)
}
