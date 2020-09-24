package com.cow.client.cowclient.packets.decoders

class Coord(
       var x: Long?,
       var y: Long

) {


    fun decode(): Coord {

        if (this.y in 1..127) {
            val xis = cordTest[this.x?.toInt()]?.toLong()
            val yos = this.y * 2
            return Coord(xis,yos)


        } else {
            val xis = cordTest[this.x?.toInt()]?.toLong()
            val yos = ((this.y - 128) * 2) + 1
            return Coord(xis, yos)

        }

    }


    var cordTest: Map<Int, Int> = mapOf(
            242 to 100,
            245 to 99,
            244 to 98,
            241 to 97,
            240 to 96,
            207 to 95,
            206 to 94,
            203 to 93,
            202 to 92,
            205 to 91,
            204 to 90,
            201 to 89,
            200 to 88,
            199 to 87,
            198 to 86,
            195 to 85,
            194 to 84,
            197 to 83,
            196 to 82,
            193 to 81,
            192 to 80,
            223 to 79,
            219 to 78,
            218 to 77,
            221 to 76,
            217 to 75,
            216 to 74,
            215 to 73,
            214 to 72,
            211 to 71,
            210 to 70,
            213 to 69,
            212 to 68,
            209 to 67,
            175 to 66,
            174 to 65,
            171 to 64,
            170 to 63,
            173 to 62,
            172 to 61,
            169 to 60,
            168 to 59,
            167 to 58,
            166 to 57,
            163 to 56,
            162 to 55,
            165 to 54,
            164 to 53,
            161 to 52,
            160 to 51,
            191 to 50,
            190 to 49,
            186 to 48,
            189 to 47,
            188 to 46,
            185 to 45,
            184 to 44,
            183 to 43,
            182 to 42,
            179 to 41,
            178 to 40,
            181 to 39,
            180 to 38,
            177 to 37,
            176 to 36,
            143 to 35,
            142 to 34,
            139 to 33,
            138 to 32,
            141 to 31,
            140 to 30,
            135 to 29,
            134 to 28,
            131 to 27,
            130 to 26,
            133 to 25,
            132 to 24,
            129 to 23,
            128 to 22,
            159 to 21,
            158 to 20,
            155 to 19,
            154 to 18,
            157 to 17,
            156 to 16,
            137 to 15,
            187 to 14,
            208 to 13,
            220 to 12,
            222 to 11
    )
}


