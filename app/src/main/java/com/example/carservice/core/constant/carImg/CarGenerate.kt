package com.example.carservice.core.constant.carImg


import com.example.carservice.R

object CarGenerate {
    fun carList(): ArrayList<CarImageModel> {
        val cars = ArrayList<CarImageModel>()

        cars.add(CarImageModel("0", "audi", R.drawable.audi))
        cars.add(CarImageModel("1", "bmw", R.drawable.bmw))
        cars.add(CarImageModel("2", "chevrolet", R.drawable.chevrolet))
        cars.add(CarImageModel("3", "citroen", R.drawable.citroen))
        cars.add(CarImageModel("4", "ferrari", R.drawable.ferrari))
        cars.add(CarImageModel("5", "fiat", R.drawable.fiat))
        cars.add(CarImageModel("6", "ford", R.drawable.ford))
        cars.add(CarImageModel("7", "honda", R.drawable.honda))
        cars.add(CarImageModel("8", "hyundai", R.drawable.hyundai))
        cars.add(CarImageModel("9", "jaguar", R.drawable.jaguar))
        cars.add(CarImageModel("10", "jeep", R.drawable.jeep))
        cars.add(CarImageModel("11", "kia", R.drawable.kia))
        cars.add(CarImageModel("12", "landrover", R.drawable.landrover))
        cars.add(CarImageModel("13", "lexus", R.drawable.lexus))
        cars.add(CarImageModel("14", "mazda", R.drawable.mazda))
        cars.add(CarImageModel("15", "mercedes", R.drawable.mercedes))
        cars.add(CarImageModel("16", "mg", R.drawable.mg))
        cars.add(CarImageModel("17", "mini", R.drawable.mini))
        cars.add(CarImageModel("18", "mitsubishi", R.drawable.mitsubishi))
        cars.add(CarImageModel("19", "nissan", R.drawable.nissan))
        cars.add(CarImageModel("20", "opel", R.drawable.opel))
        cars.add(CarImageModel("21", "peugeot", R.drawable.peugeot))
        cars.add(CarImageModel("22", "porsche", R.drawable.porsche))
        cars.add(CarImageModel("23", "renault", R.drawable.renault))
        cars.add(CarImageModel("24", "seat", R.drawable.seat))
        cars.add(CarImageModel("25", "skoda", R.drawable.skoda))
        cars.add(CarImageModel("26", "toyota", R.drawable.toyota))
        cars.add(CarImageModel("27", "volkswagen", R.drawable.volkswagen))
        cars.add(CarImageModel("28", "volvo", R.drawable.volvo))
        cars.add(CarImageModel("29", "Changan", R.drawable.changan))
        cars.add(CarImageModel("30", "Infiniti", R.drawable.infiniti))
        cars.add(CarImageModel("31", "GMC", R.drawable.gmc))
        cars.add(CarImageModel("32", "Dodge", R.drawable.dodge))
        cars.add(CarImageModel("33", "Cadillac", R.drawable.cadillac))
        cars.add(CarImageModel("34", "Lamborghini", R.drawable.lamborghini))
        cars.add(CarImageModel("35", "McLaren", R.drawable.mclaren))
        cars.add(CarImageModel("36", "Tesla", R.drawable.tesla))
        cars.add(CarImageModel("37", "Aston Martin", R.drawable.aston_martin))
        cars.add(CarImageModel("38", "BYD", R.drawable.byd))
        cars.add(CarImageModel("39", "Chery", R.drawable.chery))
        cars.add(CarImageModel("40", "Daewoo", R.drawable.daewoo))
        cars.add(CarImageModel("41", "Dong Feng", R.drawable.dongfeng))
        cars.add(CarImageModel("42", "Gee ly", R.drawable.geely))
        cars.add(CarImageModel("43", "Hummer", R.drawable.hummer))
        cars.add(CarImageModel("44", "Isuzu", R.drawable.isuzu))
        cars.add(CarImageModel("45", "JAC", R.drawable.jac))
        cars.add(CarImageModel("46", "Lincoln", R.drawable.lincoln))
        cars.add(CarImageModel("47", "Renault", R.drawable.renault))
        cars.add(CarImageModel("48", "Rolls Royce", R.drawable.rolls_royce))
        cars.add(CarImageModel("49", "Saap", R.drawable.saab))
        cars.add(CarImageModel("50", "Suzuki", R.drawable.suzuki))
        cars.add(CarImageModel("51", "Daihatsu", R.drawable.daihatsu))
        cars.add(CarImageModel("52", "Acura", R.drawable.acura))
        cars.add(CarImageModel("53", "Alfa Romeo", R.drawable.alfa_romeo))
        cars.add(CarImageModel("54", "Daihatsu", R.drawable.daihatsu))
        cars.add(CarImageModel("55", "Datsun", R.drawable.datsun))
        cars.add(CarImageModel("56", "Foton", R.drawable.foton))
        cars.add(CarImageModel("57", "Lada", R.drawable.lada))
        cars.add(CarImageModel("58", "Mercury", R.drawable.mercury))
        cars.add(CarImageModel("59", "Sang Yong", R.drawable.ssangyong))


        return cars


    }

    fun getCarByID(id: String): CarImageModel {
        for (item in carList()) {
            if (item.id.equals(id)) {
                return item
            }
        }
        return CarImageModel(id = "-1", name = "", img = R.drawable.img_car)
    }
}