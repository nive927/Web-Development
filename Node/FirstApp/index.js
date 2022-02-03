const express = require('express')

// how we setup the server using express on a particular port
// use localhost:port to find it in the browser
const app = express()
const port = 8080

// serves requests: both get and post
// from the browser
// app.use((req, res) => {
//     console.log("We got a new request!")
//     res.send("Hello there! We got your request and this is our responnse!")
// })

// specific routing
app.get('/', (req, res) => {
    res.send("Hello there!!! We got your request and this is our responnse!")
})

// matches the pattern /r/anything given there at the 
// browser - the anything is stored in params
// with key as subreddit
app.get('/r/:subreddit', (req, res) => {
    const { subreddit } = req.params
    res.send(`<h1>Browsing the ${subreddit} subreddit</h1>`)
})

app.get('/cats', (req, res) => {
    res.send("<h1>Meow !</h1>")
}) 

app.get('/dogs', (req, res) => {
    res.send("<h1>Woof !</h1>")
}) 

app.get('/search', (req, res) => {
    const { q } = req.query;
    if(!q) {
        res.send(`<h1>NO Query String Found!</h1>`)
    }
    res.send(`<h1>The query string q is ${q} !</h1>`)
}) 

// Handle all unknown paths - DON"T PUT IT ON TOP
app.get('*', (req, res) => {
    res.send("OH no! I don't know that path")
})

// starts the server
app.listen(port, () => {
    console.log("Listening on port 8080")
})

