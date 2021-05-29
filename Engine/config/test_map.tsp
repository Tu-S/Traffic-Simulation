{
  "nodes": [
    {
      "x": 131.9343493223432,
      "y": 253.8483507493779,
      "roadsIn": [
        1,
        11
      ],
      "roadsOut": [
        0,
        10
      ]
    },
    {
      "x": 611.2400809601373,
      "y": 258.85851519158496,
      "roadsIn": [
        0,
        3,
        13
      ],
      "roadsOut": [
        1,
        2,
        12
      ]
    },
    {
      "x": 1125.6169636933798,
      "y": 255.518405563447,
      "roadsIn": [
        2,
        5
      ],
      "roadsOut": [
        3,
        4
      ]
    },
    {
      "x": 1130.6271281355869,
      "y": 674.7021638947581,
      "roadsIn": [
        4,
        7,
        21
      ],
      "roadsOut": [
        5,
        6,
        20
      ]
    },
    {
      "x": 624.600519472689,
      "y": 688.0626024073098,
      "roadsIn": [
        6,
        14
      ],
      "roadsOut": [
        7,
        15
      ]
    },
    {
      "x": 146.964842648964,
      "y": 678.0422735228958,
      "roadsIn": [
        10,
        8,
        30
      ],
      "roadsOut": [
        11,
        9,
        31
      ]
    },
    {
      "x": 623.1415172741616,
      "y": 687.2124525799717,
      "roadsIn": [
        9,
        15,
        17,
        34
      ],
      "roadsOut": [
        8,
        14,
        16,
        35
      ],
      "trafficLight": [
        {
          "delay": 20,
          "roads": [
            9,
            15
          ]
        },
        {
          "delay": 30,
          "roads": [
            17,
            34
          ]
        }
      ]
    },
    {
      "x": 634.5089412800312,
      "y": 1032.3687814854632,
      "roadsIn": [
        19,
        25,
        26
      ],
      "roadsOut": [
        18,
        24,
        27
      ],
      "trafficLight": [
        {
          "delay": 20,
          "roads": [
            19,
            25
          ]
        },
        {
          "delay": 20,
          "roads": [
            26
          ]
        }
      ]
    },
    {
      "x": 1135.7089997206403,
      "y": 1022.0347596619456,
      "roadsIn": [
        18,
        20
      ],
      "roadsOut": [
        19,
        21
      ]
    },
    {
      "x": 154.014009052897,
      "y": 1011.3990564860977,
      "roadsIn": [
        24,
        22
      ],
      "roadsOut": [
        25,
        23
      ]
    },
    {
      "x": 622.1195653601324,
      "y": 841.6198391725926,
      "roadsIn": [
        16,
        27,
        29
      ],
      "roadsOut": [
        17,
        26,
        28
      ]
    },
    {
      "x": 156.43942644308987,
      "y": 835.5562956971103,
      "roadsIn": [
        28,
        23,
        31
      ],
      "roadsOut": [
        29,
        22,
        30
      ]
    },
    {
      "x": 447.48951326624154,
      "y": 488.72160889952124,
      "roadsIn": [
        33
      ],
      "roadsOut": [
        32
      ],
      "periodsOfSpawn": [
        {
          "start": "09:33",
          "end": "20:34",
          "spawnRate": 30
        }
      ]
    },
    {
      "x": 608.7797697140713,
      "y": 491.1470262897142,
      "roadsIn": [
        32,
        12,
        35,
        37
      ],
      "roadsOut": [
        33,
        13,
        34,
        36
      ],
      "trafficLight": [
        {
          "delay": 30,
          "roads": [
            32,
            37
          ]
        },
        {
          "delay": 30,
          "roads": [
            12,
            35
          ]
        }
      ]
    },
    {
      "x": 836.7690043922067,
      "y": 480.2326480338459,
      "roadsIn": [
        36
      ],
      "roadsOut": [
        37
      ],
      "periodsOfSpawn": [
        {
          "start": "09:33",
          "end": "20:34",
          "spawnRate": 30
        }
      ]
    }
  ],
  "roads": [
    {
      "from": 0,
      "to": 1,
      "lanes": [
        {
          "signs": []
        }
      ]
    },
    {
      "from": 1,
      "to": 0,
      "lanes": [
        {
          "signs": []
        }
      ]
    },
    {
      "from": 1,
      "to": 2,
      "lanes": [
        {
          "signs": []
        }
      ]
    },
    {
      "from": 2,
      "to": 1,
      "lanes": [
        {
          "signs": []
        }
      ]
    },
    {
      "from": 2,
      "to": 3,
      "lanes": [
        {
          "signs": []
        }
      ]
    },
    {
      "from": 3,
      "to": 2,
      "lanes": [
        {
          "signs": []
        }
      ]
    },
    {
      "from": 3,
      "to": 4,
      "lanes": [
        {
          "signs": [
            {
              "limit": "40",
              "type": "SPEED_LIMIT"
            }
          ]
        }
      ]
    },
    {
      "from": 4,
      "to": 3,
      "lanes": [
        {
          "signs": [
            {
              "limit": "60",
              "type": "SPEED_LIMIT"
            }
          ]
        }
      ]
    },
    {
      "from": 6,
      "to": 5,
      "lanes": [
        {
          "signs": [
            {
              "limit": "40",
              "type": "SPEED_LIMIT"
            }
          ]
        }
      ]
    },
    {
      "from": 5,
      "to": 6,
      "lanes": [
        {
          "signs": [
            {
              "limit": "60",
              "type": "SPEED_LIMIT"
            }
          ]
        }
      ]
    },
    {
      "from": 0,
      "to": 5,
      "lanes": [
        {
          "signs": [
            {
              "type": "MAIN_ROAD"
            }
          ]
        }
      ]
    },
    {
      "from": 5,
      "to": 0,
      "lanes": [
        {
          "signs": [
            {
              "type": "MAIN_ROAD"
            }
          ]
        }
      ]
    },
    {
      "from": 1,
      "to": 13,
      "lanes": [
        {
          "signs": [
            {
              "limit": "50",
              "type": "SPEED_LIMIT"
            }
          ]
        }
      ]
    },
    {
      "from": 13,
      "to": 1,
      "lanes": [
        {
          "signs": [
            {
              "limit": "20",
              "type": "SPEED_LIMIT"
            }
          ]
        }
      ]
    },
    {
      "from": 6,
      "to": 4,
      "lanes": [
        {
          "signs": []
        }
      ]
    },
    {
      "from": 4,
      "to": 6,
      "lanes": [
        {
          "signs": []
        }
      ]
    },
    {
      "from": 6,
      "to": 10,
      "lanes": [
        {
          "signs": []
        }
      ]
    },
    {
      "from": 10,
      "to": 6,
      "lanes": [
        {
          "signs": []
        }
      ]
    },
    {
      "from": 7,
      "to": 8,
      "lanes": [
        {
          "signs": []
        }
      ]
    },
    {
      "from": 8,
      "to": 7,
      "lanes": [
        {
          "signs": []
        }
      ]
    },
    {
      "from": 3,
      "to": 8,
      "lanes": [
        {
          "signs": []
        }
      ]
    },
    {
      "from": 8,
      "to": 3,
      "lanes": [
        {
          "signs": []
        }
      ]
    },
    {
      "from": 11,
      "to": 9,
      "lanes": [
        {
          "signs": [
            {
              "type": "MAIN_ROAD"
            }
          ]
        }
      ]
    },
    {
      "from": 9,
      "to": 11,
      "lanes": [
        {
          "signs": [
            {
              "type": "MAIN_ROAD"
            }
          ]
        }
      ]
    },
    {
      "from": 7,
      "to": 9,
      "lanes": [
        {
          "signs": []
        }
      ]
    },
    {
      "from": 9,
      "to": 7,
      "lanes": [
        {
          "signs": []
        }
      ]
    },
    {
      "from": 10,
      "to": 7,
      "lanes": [
        {
          "signs": []
        }
      ]
    },
    {
      "from": 7,
      "to": 10,
      "lanes": [
        {
          "signs": []
        }
      ]
    },
    {
      "from": 10,
      "to": 11,
      "lanes": [
        {
          "signs": []
        }
      ]
    },
    {
      "from": 11,
      "to": 10,
      "lanes": [
        {
          "signs": []
        }
      ]
    },
    {
      "from": 11,
      "to": 5,
      "lanes": [
        {
          "signs": [
            {
              "type": "MAIN_ROAD"
            }
          ]
        }
      ]
    },
    {
      "from": 5,
      "to": 11,
      "lanes": [
        {
          "signs": [
            {
              "type": "MAIN_ROAD"
            }
          ]
        }
      ]
    },
    {
      "from": 12,
      "to": 13,
      "lanes": [
        {
          "signs": [
            {
              "limit": "20",
              "type": "SPEED_LIMIT"
            }
          ]
        }
      ]
    },
    {
      "from": 13,
      "to": 12,
      "lanes": [
        {
          "signs": [
            {
              "limit": "40",
              "type": "SPEED_LIMIT"
            }
          ]
        }
      ]
    },
    {
      "from": 13,
      "to": 6,
      "lanes": [
        {
          "signs": []
        }
      ]
    },
    {
      "from": 6,
      "to": 13,
      "lanes": [
        {
          "signs": []
        }
      ]
    },
    {
      "from": 13,
      "to": 14,
      "lanes": [
        {
          "signs": [
            {
              "limit": "20",
              "type": "SPEED_LIMIT"
            }
          ]
        }
      ]
    },
    {
      "from": 14,
      "to": 13,
      "lanes": [
        {
          "signs": [
            {
              "limit": "20",
              "type": "SPEED_LIMIT"
            }
          ]
        }
      ]
    }
  ],
  "pointsOfInterest": [
    {
      "x": 805.2385783196987,
      "y": 389.2794959016111,
      "width": 195.2460999105308,
      "height": 156.43942166744398,
      "capacity": 0,
      "weight": 1.0,
      "nodes": [
        14
      ]
    },
    {
      "x": 217.07486119791318,
      "y": 396.55574807218994,
      "width": 248.6052824947753,
      "height": 178.26817817918044,
      "capacity": 0,
      "weight": 1.0,
      "nodes": [
        12
      ]
    }
  ],
    "start": "10:00",
    "currentTime": "10:00",
    "end": "15:00"
}