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
        return cars


    }

    fun getCarByName(id: String): CarImageModel {
        for (item in carList()) {
            if (item.id.equals(id)) {
                return item
            }
        }
        return CarImageModel(id="-1",name="", img = R.drawable.img_car)
    }
}