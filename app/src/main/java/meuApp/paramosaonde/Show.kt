package meuApp.paramosaonde

class Show (val id: Int = 0, var title: String = "", var imgUri: String = "", var ep: Int = 0) {


    override fun toString(): String {
        return "$id - $title | Episódio: $ep"
    }




}