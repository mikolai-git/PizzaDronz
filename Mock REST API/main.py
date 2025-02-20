from fastapi import FastAPI
from datetime import date
from typing import List, Dict

app = FastAPI()

# Mock data
central_area = [
    {
      "name": "central",
      "vertices": [
        {
          "lng": -3.192473,
          "lat": 55.946233
        },
        {
          "lng": -3.192473,
          "lat": 55.942617
        },
        {
          "lng": -3.184319,
          "lat": 55.942617
        },
        {
          "lng": -3.184319,
          "lat": 55.946233
        },
        {
          "lng": -3.192473,
          "lat": 55.946233
        }
      ]
    }
]

no_fly_zones = [
                 {
                   "name": "George Square Area",
                   "vertices": [
                     {
                       "lng": -3.19057881832123,
                       "lat": 55.9440241257753
                     },
                     {
                       "lng": -3.18998873233795,
                       "lat": 55.9428465054091
                     },
                     {
                       "lng": -3.1870973110199,
                       "lat": 55.9432881172426
                     },
                     {
                       "lng": -3.18768203258514,
                       "lat": 55.9444777403937
                     },
                     {
                       "lng": -3.19057881832123,
                       "lat": 55.9440241257753
                     }
                   ]
                 },
                 {
                   "name": "Dr Elsie Inglis Quadrangle",
                   "vertices": [
                     {
                       "lng": -3.19071829319,
                       "lat": 55.9451957023404
                     },
                     {
                       "lng": -3.19061636924744,
                       "lat": 55.9449824179636
                     },
                     {
                       "lng": -3.19002628326416,
                       "lat": 55.9450755422726
                     },
                     {
                       "lng": -3.19013357162476,
                       "lat": 55.945297838105
                     },
                     {
                       "lng": -3.19071829319,
                       "lat": 55.9451957023404
                     }
                   ]
                 },
                 {
                   "name": "Bristo Square Open Area",
                   "vertices": [
                     {
                       "lng": -3.18954348564148,
                       "lat": 55.9455231366331
                     },
                     {
                       "lng": -3.18938255310059,
                       "lat": 55.9455321485469
                     },
                     {
                       "lng": -3.1892591714859,
                       "lat": 55.9454480372693
                     },
                     {
                       "lng": -3.18920016288757,
                       "lat": 55.9453368899437
                     },
                     {
                       "lng": -3.18919479846954,
                       "lat": 55.9451957023404
                     },
                     {
                       "lng": -3.18913578987122,
                       "lat": 55.9451175983387
                     },
                     {
                       "lng": -3.18813800811768,
                       "lat": 55.9452738061846
                     },
                     {
                       "lng": -3.18855106830597,
                       "lat": 55.9461059027456
                     },
                     {
                       "lng": -3.18953812122345,
                       "lat": 55.9455591842759
                     },
                     {
                       "lng": -3.18954348564148,
                       "lat": 55.9455231366331
                     }
                   ]
                 },
                 {
                   "name": "Bayes Central Area",
                   "vertices": [
                     {
                       "lng": -3.1876927614212,
                       "lat": 55.9452069673277
                     },
                     {
                       "lng": -3.18755596876144,
                       "lat": 55.9449621408666
                     },
                     {
                       "lng": -3.18698197603226,
                       "lat": 55.9450567672283
                     },
                     {
                       "lng": -3.18723276257515,
                       "lat": 55.9453699337766
                     },
                     {
                       "lng": -3.18744599819183,
                       "lat": 55.9453361389472
                     },
                     {
                       "lng": -3.18737357854843,
                       "lat": 55.9451934493426
                     },
                     {
                       "lng": -3.18759351968765,
                       "lat": 55.9451566503593
                     },
                     {
                       "lng": -3.18762436509132,
                       "lat": 55.9452197343093
                     },
                     {
                       "lng": -3.1876927614212,
                       "lat": 55.9452069673277
                     }
                   ]
                 }
               ]

restaurants = [
                {
                  "name": "Civerinos Slice",
                  "location": {
                    "lng": -3.19128692150116,
                    "lat": 55.9455351525177
                  },
                  "openingDays": [
                    "MONDAY",
                    "TUESDAY",
                    "FRIDAY",
                    "SATURDAY",
                    "SUNDAY"
                  ],
                  "menu": [
                    {
                      "name": "R1: Margarita",
                      "priceInPence": 1000
                    },
                    {
                      "name": "R1: Calzone",
                      "priceInPence": 1400
                    }
                  ]
                },
                {
                  "name": "Sora Lella Vegan Restaurant",
                  "location": {
                    "lng": -3.20254147052765,
                    "lat": 55.9432847375794
                  },
                  "openingDays": [
                    "MONDAY",
                    "TUESDAY",
                    "WEDNESDAY",
                    "THURSDAY",
                    "FRIDAY"
                  ],
                  "menu": [
                    {
                      "name": "R2: Meat Lover",
                      "priceInPence": 1400
                    },
                    {
                      "name": "R2: Vegan Delight",
                      "priceInPence": 1100
                    }
                  ]
                },
                {
                  "name": "Domino's Pizza - Edinburgh - Southside",
                  "location": {
                    "lng": -3.18385720252991,
                    "lat": 55.9444987687571
                  },
                  "openingDays": [
                    "WEDNESDAY",
                    "THURSDAY",
                    "FRIDAY",
                    "SATURDAY",
                    "SUNDAY"
                  ],
                  "menu": [
                    {
                      "name": "R3: Super Cheese",
                      "priceInPence": 1400
                    },
                    {
                      "name": "R3: All Shrooms",
                      "priceInPence": 900
                    }
                  ]
                },
                {
                  "name": "Sodeberg Pavillion",
                  "location": {
                    "lng": -3.19401741027832,
                    "lat": 55.9439069661694
                  },
                  "openingDays": [
                    "TUESDAY",
                    "WEDNESDAY",
                    "SATURDAY",
                    "SUNDAY"
                  ],
                  "menu": [
                    {
                      "name": "R4: Proper Pizza",
                      "priceInPence": 1400
                    },
                    {
                      "name": "R4: Pineapple & Ham & Cheese",
                      "priceInPence": 900
                    }
                  ]
                },
                {
                  "name": "La Trattoria",
                  "location": {
                    "lng": -3.1810810679852,
                    "lat": 55.9389106437358
                  },
                  "openingDays": [
                    "MONDAY",
                    "THURSDAY",
                    "SATURDAY",
                    "SUNDAY"
                  ],
                  "menu": [
                    {
                      "name": "R5: Pizza Dream",
                      "priceInPence": 1400
                    },
                    {
                      "name": "R5: My kind of pizza",
                      "priceInPence": 900
                    }
                  ]
                },
                {
                  "name": "Halal Pizza",
                  "location": {
                    "lng": -3.18542820314392,
                    "lat": 55.945846113595
                  },
                  "openingDays": [
                    "MONDAY",
                    "TUESDAY",
                    "WEDNESDAY",
                    "SATURDAY",
                    "SUNDAY"
                  ],
                  "menu": [
                    {
                      "name": "R6: Sucuk delight",
                      "priceInPence": 1400
                    },
                    {
                      "name": "R6: Dreams of Syria",
                      "priceInPence": 900
                    }
                  ]
                },
                {
                  "name": "World of Pizza",
                  "location": {
                    "lng": -3.17979897206425,
                    "lat": 55.939884084483
                  },
                  "openingDays": [
                    "THURSDAY",
                    "FRIDAY",
                    "TUESDAY"
                  ],
                  "menu": [
                    {
                      "name": "R7: Hot, hotter, the hottest",
                      "priceInPence": 1400
                    },
                    {
                      "name": "R7: All you ever wanted",
                      "priceInPence": 900
                    }
                  ]
                },
                {
                  "name": "Far, far away",
                  "location": {
                    "lng": -3.18324378320412,
                    "lat": 55.9377340115875
                  },
                  "openingDays": [
                    "THURSDAY",
                    "FRIDAY",
                    "TUESDAY"
                  ],
                  "menu": [
                    {
                      "name": "FAR: Hot, hotter, the hottest",
                      "priceInPence": 1400
                    },
                    {
                      "name": "FAR: All you ever wanted",
                      "priceInPence": 900
                    }
                  ]
                }
              ]

orders = {
    "2025-02-20": [
        {
            "orderNo": "2539BDAC",
            "orderDate": "2025-02-20",
            "orderStatus": "UNDEFINED",
            "orderValidationCode": "UNDEFINED",
            "priceTotalInPence": 2400,
            "pizzasInOrder": [
              {
                "name": "FAR: Hot, hotter, the hottest",
                "priceInPence": 1400
              },
              {
                "name": "FAR: All you ever wanted",
                "priceInPence": 900
              }
            ],
            "creditCardInformation": {
              "creditCardNumber": "1212121212121212",
              "creditCardExpiry": "03/26",
              "cvv": "784"
            }
          },
          {
            "orderNo": "5BA9C188",
            "orderDate": "2025-02-20",
            "orderStatus": "INVALID",
            "orderValidationCode": "EXPIRY_DATE_INVALID",
            "priceTotalInPence": 2400,
            "pizzasInOrder": [
              {
                "name": "FAR: Hot, hotter, the hottest",
                "priceInPence": 1400
              },
              {
                "name": "FAR: All you ever wanted",
                "priceInPence": 900
              }
            ],
            "creditCardInformation": {
              "creditCardNumber": "4333533397104746",
              "creditCardExpiry": "17/17",
              "cvv": "351"
            }
          },
          {
            "orderNo": "42B87B71",
            "orderDate": "2025-02-20",
            "orderStatus": "INVALID",
            "orderValidationCode": "CVV_INVALID",
            "priceTotalInPence": 2600,
            "pizzasInOrder": [
              {
                "name": "R2: Meat Lover",
                "priceInPence": 1400
              },
              {
                "name": "R2: Vegan Delight",
                "priceInPence": 1100
              }
            ],
            "creditCardInformation": {
              "creditCardNumber": "4894487416462604",
              "creditCardExpiry": "01/26",
              "cvv": "1376"
            }
          },
          {
            "orderNo": "29930729",
            "orderDate": "2025-02-20",
            "orderStatus": "INVALID",
            "orderValidationCode": "TOTAL_INCORRECT",
            "priceTotalInPence": 2382,
            "pizzasInOrder": [
              {
                "name": "R3: Super Cheese",
                "priceInPence": 1400
              },
              {
                "name": "R3: All Shrooms",
                "priceInPence": 900
              }
            ],
            "creditCardInformation": {
              "creditCardNumber": "4131065885742616",
              "creditCardExpiry": "06/25",
              "cvv": "172"
            }
          },
          {
            "orderNo": "75F8FD4D",
            "orderDate": "2025-02-20",
            "orderStatus": "INVALID",
            "orderValidationCode": "PIZZA_NOT_DEFINED",
            "priceTotalInPence": 498705280,
            "pizzasInOrder": [
              {
                "name": "R3: Super Cheese",
                "priceInPence": 1400
              },
              {
                "name": "R3: All Shrooms",
                "priceInPence": 900
              },
              {
                "name": "Pizza-Surprise ",
                "priceInPence": 498702880
              }
            ],
            "creditCardInformation": {
              "creditCardNumber": "4983916120249651",
              "creditCardExpiry": "08/25",
              "cvv": "435"
            }
          },
          {
            "orderNo": "486A3136",
            "orderDate": "2025-02-20",
            "orderStatus": "INVALID",
            "orderValidationCode": "MAX_PIZZA_COUNT_EXCEEDED",
            "priceTotalInPence": 8000,
            "pizzasInOrder": [
              {
                "name": "R7: Hot, hotter, the hottest",
                "priceInPence": 1400
              },
              {
                "name": "R7: All you ever wanted",
                "priceInPence": 900
              },
              {
                "name": "R7: Hot, hotter, the hottest",
                "priceInPence": 1400
              },
              {
                "name": "R7: Hot, hotter, the hottest",
                "priceInPence": 1400
              },
              {
                "name": "R7: Hot, hotter, the hottest",
                "priceInPence": 1400
              },
              {
                "name": "R7: Hot, hotter, the hottest",
                "priceInPence": 1400
              }
            ],
            "creditCardInformation": {
              "creditCardNumber": "4937289384572086",
              "creditCardExpiry": "11/25",
              "cvv": "837"
            }
          },
          {
            "orderNo": "37FA427A",
            "orderDate": "2025-02-20",
            "orderStatus": "INVALID",
            "orderValidationCode": "PIZZA_FROM_MULTIPLE_RESTAURANTS",
            "priceTotalInPence": 3800,
            "pizzasInOrder": [
              {
                "name": "R3: Super Cheese",
                "priceInPence": 1400
              },
              {
                "name": "R3: All Shrooms",
                "priceInPence": 900
              },
              {
                "name": "R2: Meat Lover",
                "priceInPence": 1400
              }
            ],
            "creditCardInformation": {
              "creditCardNumber": "4377450984663934",
              "creditCardExpiry": "03/25",
              "cvv": "964"
            }
          }
    ]
}


@app.get("/")
def root():
    return {"status": "OK"}

@app.get("/centralArea")
def get_central_area():
    return {"centralArea": central_area}  # Wrap in an object

@app.get("/noFlyZones")
def get_no_fly_zones():
    return no_fly_zones

@app.get("/restaurants")
def get_restaurants():
    return restaurants

@app.get("/orders/{order_date}")
def get_orders_by_date(order_date: str):
    if order_date in orders:
        return orders[order_date]  # ✅ Returns a list directly
    return []  # ✅ Returns an empty list

@app.get("/orders")
def get_all_orders():
    all_orders = []
    for date_orders in orders.values():
        all_orders.extend(date_orders)
    return all_orders  # ✅ Returns a list directly
