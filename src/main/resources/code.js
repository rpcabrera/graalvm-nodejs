
const webpush = require('web-push');

webpush.setVapidDetails(
    'https://appollo-systems@googlegroups.com',
    "BG6sKtxfgeA4-hESwTtanqXyOvWiTTXyHy43gCrQLjb_-8EXlf6PATzLY1j2d0kgl6OuWV9a0LEJdnTicxuHCYo",
    "9GYQ8UXlegkReZ3E3t8gHWJ1LROYeI5_gPAr4XFfTMQ"
);

const pushSubscription = {
    endpoint: 'https://web.push.apple.com/QKjTeFhntn7YPLAp7jykPl4ozChwvU5IIpZsvcwsU1TxYErrkaOvo04El1d0f_wLkhoIE4QqV6qUIgYOZS_73Rfc83ar04XsN05yYj8_0IYYC43ImkhcWCi-ZY9XVtG738-GFhHXDLXc6vy9eFNJdfEA5p4gGQScjIG72-_BytU',
    keys: {
        auth: "8hb1D98UHuWRJ/sWbwDdBQ==",
        p256dh: "BEHGDIgeYFheUoUdVP3tnZtfZHlh+NJS27/0eqea4Q2V0riy9XPiPiY1d4CPagbB94ekYG/j34uPI9bprjFWf2E=",
    }
};
let message = {
    "title": "Message title",
    "message": "Message content"
}

let requestOptions = {
    contentEncoding: 'aes128gcm',
    headers: {
        'Urgency': 'high',
        'TTL': 0,
        'Topic': 'aps-app'
    }
}

console.log('Sending the notification: ');
console.log(JSON.stringify(webpush.generateRequestDetails(
    pushSubscription,
    JSON.stringify(message),
    requestOptions
)));

webpush.sendNotification(pushSubscription, JSON.stringify(message), requestOptions)
    .then(result => {
        console.log(`Result from the Push Service:`);
        console.log(JSON.stringify(result));
    })
    .catch(error => {
        console.log(`Error sending the Push Notification: `);
        console.log(error);
    })