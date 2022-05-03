const express = require('express')
const fetch = require('node-fetch')
const router = express.Router();
const colors = require('colors');


router.get('/btc', async (req, res, next) => {

    const urlBtcInfo = 'https://api.bitso.com/v3/ticker/?book=btc_mxn'

    try {

        const request = await fetch(urlBtcInfo)
        const result = await request.json()
        
        console.log('BTC last: ', result.payload.last.rainbow)
        res.status(200).send(result)
        
    } catch (error) {

        res.status(400).send(error)
    }

})
