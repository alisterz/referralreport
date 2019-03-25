# Generating Report
### Sample Payload
```javascript

[
  {
    "name": "Acme, Inc.", "close_date": "01-12-2018", "referred_by": "Sprockets"
  },
  {
    "name": "Sprockets", "close_date": "01-09-2018", "referred_by": "Dunder Mifflin"
  },
  {
    "name": "Initech", "close_date": "01-01-2018"
  },
  {
    "name": "Initrode", "close_date": "02-02-2018", "referred_by": "Initech"
  },
  {
    "name": "L&P Construction", "close_date": "01-19-2018", "referred_by": "Initech"
  },
  {
    "name": "Dunder Mifflin", "close_date": "12-01-2017"
  }
]
```

### Algorithm
The way i took is to use 2 maps.
User Map and Monthly Count Map (Per Month Per User)

***User Map***

name | referredBy
--- | ---
Acme, Inc | Sprocket
Initech | null
Initrode | Initech


***Monthly Count Map***

date | countMap
--- | ---
01/2018 | Sprocket:1
 ---    | Initech:1
 
After populating both map, a clean up work is done to set any indirect referral to its root referral. 

After the clean up, while printing the result, each countMap will be consolidated, indirect referral is replaced by direct referral. 



