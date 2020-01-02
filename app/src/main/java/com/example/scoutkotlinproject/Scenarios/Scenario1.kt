package com.example.scoutkotlinproject.Scenarios

import android.content.Context
import android.widget.RelativeLayout
import android.widget.Toast
import com.example.scoutkotlinproject.R
import com.example.scoutkotlinproject.Scenario

class Scenario1 (c: Context,l:RelativeLayout) : Scenario(c,l) {
    var skill: Int = 0
    var choix: Int = 0
    var jeu: Int = 0 //000

    override fun RestoreSave(s: String,verif:Int, lVerif:Int):Boolean {
        if(super.RestoreSave(s,1,7)){
            prog = s[1].toString().toInt()
            skill = s[2].toString().toInt()
            choix = s[3].toString().toInt()
            jeu = s.substring(4).toInt()
        }
        else{
            if(s.length>=2)prog = s[1].toString().toInt()
            if(s.length>=3)skill = s[2].toString().toInt()
            if(s.length>=4)choix = s[3].toString().toInt()
            if(s.length>=5)choix = s.substring(4,5).toInt()
            if(s.length>=6)choix = s.substring(4,6).toInt()
            if(s.length>=7)choix = s.substring(4,7).toInt()

        }

        return true
    }

    override fun SendSave(): String {
        if(jeu<10)
            return "1$prog$skill$choix"+"00"+"$jeu"
        if(jeu<100)
            return "1$prog$skill$choix"+"0"+"$jeu"
        return "1$prog$skill$choix$jeu"
    }


    override fun Beggin() {
        when(prog)
        {
            0->InstanceTextAt("Bienvenue a vous, jeunes voyageurs. Vous etes les derniers apprentis du Kai, une  Le monde a besoin de vous. Un terrible sorcier du nom de Vonotar s'est installé dans les terres reculées de Kaltes, et vous seuls pouvez l'arreter. \n Dans cette epreuve, l'art du Kai pourra vous etre utile, et votre maitre peut vous enseigner une technique avant de partir. Choisissez la bien, et mettez vous en route. Une grande aventure vous attends !" ,600   )
            in 1..6->this.javaClass.getMethod("Etape$prog").invoke("")
            else -> InstanceTextAt("La sauvegarde est corrompue. Entrer le code Reset pour remettre à zero.")
        }
        InstanceImageAt("s1start")

    }

    override fun GetImage(): Int {
        when (prog){
            0,1->return R.drawable.s1capture2
            2->if(jeu!=1)return R.drawable.s1capture3 else return R.drawable.s1capture3_1
            3->return R.drawable.s1capture4
            4->return R.drawable.s1capture5
            5->return R.drawable.s1capture6
            else->return super.GetImage()
        }
    }

    override fun GetAns(S: String):Boolean {
        when (S) {
            "reset","Reset"->Reset()
            "Force 1","Force 2","Force 3","Force 4","Force 5","Force 6","Force 7","Force 8"->prog=S[6].toInt()
            "Technique Kai : Camouflage" -> skill = 1
            "Technique Kai : 6e sens" -> skill = 2
            "Technique Kai : Telekinesie" -> skill = 3
            "Etape 1 : Quelle direction ?" -> Etape1()
            "Passons par la vallee !" -> choix = 1
            "Passons par la montagne" -> choix = 2
            "Etape 2 : Avancer !" -> Etape2()
            "De l'eau !" -> Etape2v0()
            "Loup 1" -> Etape2v1(1)
            "Loup 2" -> Etape2v1(10)
            "Loup 3" -> Etape2v1(100)
            "Etape 3 : Chateau en vue" -> Etape3()
            "Etape 4 : Entree du chateau" -> Etape4()
            "5 - Porte" -> Etape4v(0)
            "5 - Escalier" -> Etape4v(1)
            "5 - Telekinesie" -> Etape4v(2)
            "Etape 5 : Exploration" ->Etape5()
            "Etape 6 : Cachots" -> Etape6()

            else -> return false
        }
        return true
    }

    fun Etape1() {
        if (!CheckProg(0)) return
        ClearLayout()
        if (skill == 0) {
            skill = 9
            InstanceTextAt("Attention ! Vous n'avez pas appris de techniques ! Attention, le voyage risque d'être périlleux ! \n Si vous etes sûr de vous, vous pouvez continuer en rescannant le message !")
            return
        }
        InstanceTextAt("Vous voila parti pour traverser la contrée gelée de Kalte. Ici deux choix s'offrent à vous : vous pouvez passer par les montagnes, route dangereuse, mais plus courte, ou bien contourner par la vallée, route plus sûre, mais plus longue ! Choisissez, et avancez !")
        prog=2
    }

    fun Etape2() {
        if (!CheckProg(2)) return
        if (choix != 1 && choix != 2) {
            InstanceTextAt("Vous n'avez pas choisi de chemin. Vous marchez aleatoirement et vous vous retouvez dans la montagne. Rescannez cette étape pour continuer ...")
            choix=2
            return
        }
        if (jeu == 0 || jeu == 1) {
            jeu=1
            ClearLayout()
            if (choix == 0)
                InstanceTextAt("Vous avez deja marché 2 jours dans la valée. Vous dressez un campement ici, mais vous n'avez pas assez d'eau. Vous devez aller en chercher à la rivière qui est marquée sur votre carte")
            else
                InstanceTextAt("Vous avancez prudement dans les montagnes depuis un jour sans encombre, et la nuit commence à tomber, qaund tout à coup vous entendez le hurlement d'un loup. Ils sont autours de vous ! Vite, trouvez les, avant qu'ils ne vous trouvent ! ")
        } else {
            if (choix == 0) {
                InstanceTextAt(
                    "Bravo, vous avez de l'eau. Vous continuez votre chemin dans la valée !",
                    800
                )
                prog = 3
            } else {
                if (jeu == 111) {
                    InstanceTextAt(
                        "Bravo, vous vous etes bien défendu. Le jour se lève, et vous pouvez repartir !",
                        1000
                    )
                    prog = 3
                } else
                    InstanceTextAt("Vous n'avez pas encore trouvé tout les loups", 800)
            }
        }
    }

    fun Etape2v0() {
        if (!CheckProg(2)) return
        jeu = 2
        InstanceTextAt("Vous avez l'eau. Revenez au campement !", 400)
    }

    fun Etape2v1(a: Int) {
        if (a == 1 && jeu % 2 != 1) {
            InstanceTextAt("Bravo, vous avez tué un loup !", 300)
            jeu += 1
        }
        if (a == 10 && (jeu / 10) % 2 != 1) {
            InstanceTextAt("Bravo, vous avez tué un loup !", 400)
            jeu += 10
        }
        if (a == 100 && (jeu / 100) % 2 != 1) {
            InstanceTextAt("Bravo, vous avez tué un loup !", 500)
            jeu += 100
        }
    }

    fun Etape3() {
        if (!CheckProg(3)) return
        ClearLayout()
        if (choix == 1) {
            InstanceTextAt("Le chateau est en vue, vous pouvez le voir se dresser au fond de la vallée. De loin, vous pouvez voir des silhouettes au pied du chateau, probablement les membres d'une tribu délogée de ses terres par le sorcier. Le temps d'arriver au chateau, vous avez le temps de décider si vous voulez aller leur demander de l'aide ou non. Si oui allez les voir, ils portent un habit couleur kaki. Si non, allez directement jusqu'à la porte du chateau")
        } else {
            InstanceTextAt("Le chateau est en vue, a flanc de la montagne voisine. En commencant la descente, vous sentez le sol se dérober sous vos pieds. Vous atterissez plus bas sur un tas de neige. Vous etes dans une grotte immense. Un passage ouvragé se découpe au loin, surement une entrée cachée du chateau. Mais il y a aussi des lumières dans un autre passage, probablement une tribu de Kalte qui vit dans ces grottes. Vous pouvez allez leur demander de l'aide, dans ce cas allez les voir, ils portent un habit couleur kaki, sinon allez directement jusqu'à la porte du chateau")
        }
        prog = 4
    }

    fun Etape4() {
        if (!CheckProg(4)) return
        ClearLayout()
        jeu= 0
        InstanceTextAt("Apres avoir passer la cour du chateau vous entrez dans une grande salle, avec une statue à l'air maléfique en son centre. Vous sentez qu'il ne faut pas la toucher. Il y a une porte sur le coté et un escalier au fond. Si vous maitrisez la telekinesie vous pouvez essayer de déplacer la statue.")
    }

    fun Etape4v(a:Int) {
        if (!CheckProg(4)) return
        if(jeu == 1)
        {
            ClearLayout()
            InstanceTextAt("Vous changez d'avis.Vous revenez au centre la pièce. Explorez la à nouveau pour pouvoir choisir autre chose.")
            prog=4
            return
        }
        jeu = 1
        when(a){
            0->InstanceTextAt("Vous vous avancez vers la porte ... ",800)
            1->InstanceTextAt("Vous vous engagez dans l'escalier ... ",800)
            2->if(skill==2) InstanceTextAt("Vous soulevez la pierre avec la force de votre mental. Une trappe secrète part de dessous. Vous vous y engagez",800)else InstanceTextAt("Vous ezzayez de bouger la pierre par la pensée mais rien ne se passe",600)
        }
        choix = a
        prog = 5
    }
    fun Etape5() {
        if (!CheckProg(5) && !CheckProg(6)) return
        ClearLayout()
        when(choix){
            0->{InstanceTextAt("Vous vous retrouvez dans une grande tour. Son toit est en bois. Si seulement vous conaissiez un sort pour l'enflammer. Si vous en connaissez un vous pouvez l'ecrire, sinon vous pouvez continuer");InstanceButtonAt("Lancer un sort",800,this::Etape5v,InstanceReaderAt("",400).text)}
            1->InstanceTextAt("Vous descendez des escaliers, en montez d'autres. Vous arrivez en d'une tour de guet en pierre. Admirez donc la vue. Puis vous repartez par un autre chemin")
            2->{InstanceTextAt("Apres être descendu puis montés un moment, vous arrivez en haut d'une tour de guet. Admirez donc la vue. Son toit est en bois. L'un d'entre vous ayant sur lui un sort de feu,vous decidez d'enflammer la tour pour signaler votre position aux éventuels renforts qui arriveraient. Vous redescendez à tout allure et continuez dans une autre chemin.");skill=4}
        }
        prog = 7
    }

    fun Etape5v(s:String)
    {
        if(s=="Pyromancio" || s== "pyromancio")
        {
            ClearLayout()
            InstanceTextAt("Vous vous retrouvez dans une grande tour. Son toit est en bois. Si seulement vous conaissiez un sort pour l'enflammer. Si vous en connaissez un vous pouvez l'ecrire, sinon vous pouvez continuer")
            InstanceTextAt("Le toit s'enflamme d'un coup. Vous sortez de la tour en courant et empruntez un autre chemin",600)
        }
    }

    fun Etape6()
    {
        if (!CheckProg(6)) return
        ClearLayout()
        InstanceTextAt("Vous arrivez dans les chachots du chateau. Ca a l'air d'un cul de sac. Il y a quatres portes de cellules. Vous decidez d'en ouvrir une !")

    }


    override fun Reset() {
        prog = 0
        skill=0
        choix=0
        jeu=0
    }
}