{
  "nodes": [
    {
      "roadsOut": [
        11
      ],
      "roadsIn": [
        10
      ],
      "x": 234,
      "y": 71
    },
    {
      "roadsOut": [
        3,
        0
      ],
      "roadsIn": [
        2,
        1
      ],
      "x": 528,
      "y": 59
    },
    {
      "roadsOut": [
        2,
        5
      ],
      "roadsIn": [
        3,
        4
      ],
      "x": 536,
      "y": 207
    },
    {
      "roadsOut": [
        7
      ],
      "roadsIn": [
        6
      ],
      "periodsOfSpawn": [
        {
          "start": "13:05",
          "end": "13:05",
          "spawnRate": 30
        }
      ],
      "x": 252,
      "y": 214
    },
    {
      "roadsOut": [
        4,
        6,
        9
      ],
      "roadsIn": [
        5,
        7,
        8
      ],
      "trafficLight": [
        {
          "delay": 30,
          "roads": [
            5,
            7
          ]
        },
        {
          "delay": 30,
          "roads": [
            8
          ]
        }
      ],
      "x": 415,
      "y": 208
    },
    {
      "roadsOut": [
        8,
        1,
        10
      ],
      "roadsIn": [
        9,
        0,
        11
      ],
      "x": 402,
      "y": 56
    }
  ],
  "roads": [
    {
      "from": 1,
      "to": 5,
      "lanes": [
        {
          "signs": []
        }
      ]
    },
    {
      "from": 5,
      "to": 1,
      "lanes": [
        {
          "signs": [
            {
              "type": "SPEED_LIMIT",
              "limit": 60
            }
          ]
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
      "from": 1,
      "to": 2,
      "lanes": [
        {
          "signs": []
        }
      ]
    },
    {
      "from": 4,
      "to": 2,
      "lanes": [
        {
          "signs": []
        }
      ]
    },
    {
      "from": 2,
      "to": 4,
      "lanes": [
        {
          "signs": []
        }
      ]
    },
    {
      "from": 4,
      "to": 3,
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
          "signs": []
        }
      ]
    },
    {
      "from": 5,
      "to": 4,
      "lanes": [
        {
          "signs": [
            {
              "type": "MAIN_ROAD",
              "limit": 0
            }
          ]
        }
      ]
    },
    {
      "from": 4,
      "to": 5,
      "lanes": [
        {
          "signs": []
        }
      ]
    },
    {
      "from": 5,
      "to": 0,
      "lanes": [
        {
          "signs": []
        }
      ]
    },
    {
      "from": 0,
      "to": 5,
      "lanes": [
        {
          "signs": []
        }
      ]
    }
  ],
  "pointsOfInterest": [
    {
      "capacity": 0,
      "nodes": [
        0,
        3
      ],
      "x": 209.0,
      "y": 41.0,
      "width": 76.0,
      "height": 215.0,
      "weight": 1.0
    }
  ],
  "trafficParticipants": [
    {
      "position": {
        "currentLane": 0,
        "position": 0.0,
        "currentRoad": 0
      },
      "car": {
        "speed": 0.0,
        "timeLeft": 0.0,
        "maxSpeed": 120.0,
        "path": [
          0,
          2
        ]
      }
    },
    {
      "position": {
        "currentLane": 0,
        "position": 0.0,
        "currentRoad": 0
      },
      "car": {
        "speed": 0.0,
        "timeLeft": 0.0,
        "maxSpeed": 120.0,
        "path": [
          0,
          2
        ]
      }
    },
    {
      "position": {
        "currentLane": 0,
        "position": 0.0,
        "currentRoad": 1
      },
      "car": {
        "speed": 0.0,
        "timeLeft": 0.0,
        "maxSpeed": 120.0,
        "path": [
          0,
          2
        ]
      }
    },
    {
      "position": {
        "currentLane": 0,
        "position": 0.0,
        "currentRoad": 2
      },
      "car": {
        "speed": 0.0,
        "timeLeft": 0.0,
        "maxSpeed": 120.0,
        "path": [
          0,
          2
        ]
      }
    }
  ],
  "start": "10:00",
  "currentTime": "10:10",
  "end": "15:00"
}