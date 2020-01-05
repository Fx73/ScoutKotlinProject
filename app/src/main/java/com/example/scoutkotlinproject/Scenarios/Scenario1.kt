package com.example.scoutkotlinproject.Scenarios

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import com.example.scoutkotlinproject.R
import com.example.scoutkotlinproject.Scenario
import java.lang.StringBuilder
import kotlin.random.Random

class Scenario1 (c: Context,l:RelativeLayout) : Scenario(c,l) {
    var skill: Int = 0
    var choix: Int = 0
    var found : Boolean = false
    var jeu: Int = 0 //000

    val animals = mutableListOf("MOUTON","COCHON","CHIEN","HIPPOPOTAME","CHEVRE","CHIEN","ZEBRE","HAMSTER","VACHE","POULE")
    val films = mutableListOf("L'AGE DE GLACE","BATMAN","SHREK", "RETOUR VERS LE FUTUR","ALADDIN","PRINCESSE MONONOKE","MATRIX","LES VISITEURS","LE DINER DE CONS","LE CERCLE DES POETES DISPARUS","OSS 117","LE MAGICIEN D'OZ","RAIPONCE","LA REINE DES NEIGES","ALICE AU PAYS DES MERVEILLES","TOY STORY","PETER PAN","POCAHONTAS","LE ROI LION","PIRATES DES CARAIBES")
    var filmsbool = mutableListOf(false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false)
    val repliques = mutableListOf("T'es mal placé dans la chaîne alimentaire pour faire ta grande gueule !","Qui que je sois au fond de moi, je ne suis jugé que par mes actes","Les ogres, c'est comme les oignons !\n Snif ! Ils shlinguent ?","Je vous vois mal rentrer dans une droguerie et... et demander du plutonium.","Tu as confiance en moi ?","Princesse ! Retourne dans ta forêt ! Ne meurs pas en vain ! La retraite est aussi une preuve de courage !","La cuillère n’existe pas !","Okay !","Il a une belle tête de vainqueur!","Carpe Diem. Profitez du jour présent. Que vos vies soient extraordinaires","J’aime me beurrer la biscotte","J’ai l’impression que nous ne sommes plus au Kansas.","Va ! Accomplis ton rêve !\n D'accord !\n Pas toi ! Ton rêve est nul !","Qu'il est choux ! On dirait un tout petit bébé licorne","Qu’on leur coupe la tête!","Vers l'infini et au dela","Tu vois ce moment entre le sommeil et le rêve, où on se souvient d'avoir rêvé ? C'est la que je t'aimerai toujours et que je t'attendrai !","Un jour tu verras, ton coeur chantera, et tu comprendra","L'amour brille sous les étoiles","je n'ai de sympathie pour aucun de vous, bande d'asticots, ni même la patience de prétendre le contraire !")
    val mRecorder :MediaRecorder = MediaRecorder()

    override fun RestoreSave(s: String,sVerif:Int, lVerif:Int):Boolean {
        if(super.RestoreSave(s,1,9)){
            prog = s.substring(1,3).toInt()
            skill = s[3].toString().toInt()
            choix = s[4].toString().toInt()
            found = s[5].toString().toInt().toBoolean()
            jeu = s.substring(6).toInt()
        }
        else{
            if(s.length>=3)prog = s.substring(1,3).toInt()
            if(s.length>=4)skill = s[3].toString().toInt()
            if(s.length>=5)choix = s[4].toString().toInt()
            if(s.length>=6)found = s[5].toString().toInt().toBoolean()
            if(s.length>=7)jeu = s.substring(6,7).toInt()
            if(s.length>=8)jeu = s.substring(6,8).toInt()
            if(s.length>=9)jeu = s.substring(6,9).toInt()

        }

        return true
    }

    override fun SendSave(): String {
        val save = StringBuilder()
        save.append("1")
        if(prog<10)
            save.append("0")
        save.append("$prog")
        save.append("$skill")
        save.append("$choix")
        save.append(found.toInt().toString())

        if(jeu<100)
            save.append("0")
        if(jeu<10)
            save.append("0")
        save.append("$jeu")

        return save.toString()
    }

    override fun GetImage(): Int {
        when (prog){
            0,1->return R.drawable.s1capture1
            2->return if(jeu!=1) R.drawable.s1capture2 else R.drawable.s1capture2_1
            3->return R.drawable.s1capture3
            4->return R.drawable.s1capture4
            5->return R.drawable.s1capture5
            6->return R.drawable.s1capture6
            7->return if(jeu!=2)R.drawable.s1capture7 else R.drawable.s1capture8
            8->return R.drawable.s1capture8
            9->return R.drawable.s1capture9
            10->return R.drawable.s1capture10
            11->return R.drawable.s1capture11
            else->return super.GetImage()
        }
    }

    override fun Beggin() {
        when(prog)
        {
            0->{ClearLayout();InstanceTextAt("Bienvenue a vous, jeunes voyageurs. Vous etes les derniers apprentis du Kai, une  Le monde a besoin de vous. Un terrible sorcier du nom de Vonotar s'est installé dans les terres reculées de Kaltes, et vous seuls pouvez l'arreter. \n Dans cette epreuve, l'art du Kai pourra vous etre utile, et votre maitre peut vous enseigner une technique avant de partir. Choisissez la bien, et mettez vous en route. Une grande aventure vous attends !" ,600   );        InstanceImageAt("s1start");prog=1 }
            1->if(found)Etape1()else{prog=0;Beggin()}
            2->if(found)Etape2()else Etape1()
            3->if(found)Etape3()else Etape2()
            4->if(found)Etape4()else Etape3()
            5->if(found)Etape5()else Etape4v(choix)
            6->if(found)Etape6()else Etape5r()
            7->if(found)Etape7()else Etape6v2v("")
            8->if(found){if(jeu==2)Etape8v1()else if(jeu==0) Etape8() else Etape8v0() }else Etape7r()
            9->if(found)Etape9()else Etape8r()
            10->if(found)Etape10()else Etape9r()
            11->if(found)Etape11()else Etape10r()
            99->Etape6v0()
            98->Etape6v1()
            97->Etape7v0()
            else -> InstanceTextAt("La sauvegarde est corrompue. Entrer le code Reset pour remettre à zero.")
        }

    }


    override fun GetAns(S: String):Boolean {
        if(S.length>=8 && S.toNormalCase().substring(0,6) == "FORCER") {
            prog = S.substring(7).toInt()
            return true
        }
        when (S.toNormalCase()) {
            "DEBUG"->ShowToast("Progression $prog \nChoix $choix \nSkill $skill \nFound $found \nJeu $jeu")
            "COCHON"-> ShowToast("Tu es une incroyable personne :-)")
            "RESET"->{Reset();Beggin()}
            "E0 CAMOUFLAGE" -> skill = 1
            "E0 6E SENS" -> skill = 2
            "E0 TELEKINESIE" -> skill = 3
            "ETAPE 1 : QUELLE DIRECTION ?" -> Etape1()
            "E1 VALLEE" -> if(choix!=0){choix = 1;InstanceImageAt("s1valley",660)}
            "E1 MONTAGNE" -> if(choix!=0){choix = 2;InstanceImageAt("s1mountain",660)}
            "ETAPE 2 : AVANCER !" -> Etape2()
            "E2 DE L'EAU !" -> Etape2v0()
            "E2 LOUP 1" -> Etape2v1(1)
            "E2 LOUP 2" -> Etape2v1(10)
            "E2 LOUP 3" -> Etape2v1(100)
            "ETAPE 3 : CHATEAU EN VUE" -> Etape3()
            "ETAPE 4 : ENTREE DU CHATEAU" -> Etape4()
            "E4 PORTE" -> Etape4v(0)
            "E4 ESCALIER" -> Etape4v(1)
            "E4 TELEKINESIE" -> Etape4v(2)
            "ETAPE 5 : EXPLORATION" ->Etape5()
            "ETAPE 6 : CACHOTS" -> Etape6()
            "E6 PORTE 0"->Etape6v0()
            "E6 PORTE 1"->Etape6v1()
            "E6 PORTE 2"->Etape6v2()
            "E6 PORTE 3"->Etape6v3()
            "ETAPE 7 : DONJON BAS"->Etape7()
            "E7 COMBAT"->Etape7v0()
            "E7 FUITE"->Etape7v1()
            "E7 CAMOUFLAGE"->Etape7v2()
            "E7 PASSAGE"->Etape7v3()
            "ETAPE 8 : DONJON MILIEU"->Etape8()
            "E8 GAUCHE"->Etape8v0()
            "E8 DROITE"->Etape8v1()
            "E8 6E SENS"->Etape8v2()
            "ETAPE 9 : DONJON HAUT"->Etape9()
            "ETAPE 10 : BOSS"->Etape10()
            "ETAPE 11 : FIN"->Etape11()
            else -> return false
        }
        return true
    }

    fun Etape1() {
        if (!CheckProg(1,2)) return
        found = true
        ClearLayout()
        if (skill == 0) {
            skill = 9
            InstanceTextAt("Attention ! Vous n'avez pas appris de techniques ! Attention, le voyage risque d'être périlleux ! \n Si vous etes sûr de vous, vous pouvez continuer en rescannant le message !")
            return
        }
        InstanceTextAt("Vous voila parti pour traverser la contrée gelée de Kalte. Ici deux choix s'offrent à vous : vous pouvez passer par les montagnes, route dangereuse, mais plus courte, ou bien contourner par la vallée, route plus sûre, mais plus longue ! Choisissez, et avancez !")
        prog=2
        found=false
        choix=3
    }

    fun Etape2() {
        if (!CheckProg(2,3)) return
        found = true
        ClearLayout()

        if (choix != 1 && choix != 2) {
            InstanceTextAt("Vous n'avez pas choisi de chemin. Vous marchez aleatoirement et vous vous retouvez dans la montagne. Rescannez cette étape pour continuer ...")
            choix=2
            return
        }
        if (choix == 1) {
                InstanceTextAt("Vous avez deja marché 2 jours dans la valée. Vous dressez un campement ici, mais vous n'avez pas assez d'eau. Vous devez aller en chercher à la rivière qui est marquée sur votre carte")
                InstanceImageAt("s1valley2",480)
        }
        else {
                InstanceTextAt("Vous avancez prudement dans les montagnes depuis un jour sans encombre, et la nuit commence à tomber, quand tout à coup vous entendez le hurlement d'un loup. Puis d'autres. Ils sont 3 ! Ils sont autours de vous ! Vite, trouvez les, avant qu'ils ne vous trouvent ! ")
                InstanceImageAt("s1mountain2",640)
        }

        if (choix == 1 && jeu == 2) {
                InstanceTextAt("Bravo, vous avez de l'eau. Vous continuez votre chemin dans la vallée !",  1100  )
                prog = 3
                found = false
        }
        if (choix == 2 && jeu in 111..113) {
                InstanceTextAt(
                    "Bravo, vous vous etes bien défendu. Le jour se lève, et vous pouvez repartir !",
                    1300
                )
                prog = 3
                found = false
            }
    }

    fun Etape2v0() {
        if (!CheckProg(2)) return
        if(choix==2)return
        jeu = 2
        InstanceTextAt("Vous avez l'eau. Revenez au campement !", 1100)
    }

    fun Etape2v1(a: Int) {
        if (a == 1 && jeu % 2 != 1) {
            InstanceTextAt("Bravo, vous avez occis un loup !", 1260)
            jeu += 1
        }
        if (a == 10 && (jeu / 10) % 2 != 1) {
            InstanceTextAt("Bravo, vous avez occis un loup !", 1360)
            jeu += 10
        }
        if (a == 100 && (jeu / 100) % 2 != 1) {
            InstanceTextAt("Bravo, vous avez occis un loup !", 1460)
            jeu += 100
        }
    }

    fun Etape3() {
        if (!CheckProg(3,4)) return
        ClearLayout()
        InstanceImageAt("s1fortress")
        if (choix == 1) {
            InstanceTextAt("Le chateau est en vue, vous pouvez le voir se dresser au fond de la vallée. De loin, vous pouvez voir des silhouettes au pied du chateau, probablement les membres d'une tribu délogée de ses terres par le sorcier. Le temps d'arriver au chateau, vous avez le temps de décider si vous voulez aller leur demander de l'aide ou non. Si oui allez les voir, ils portent un habit couleur kaki. Si non, allez directement jusqu'à la porte du chateau",600)
        } else {
            InstanceTextAt("Le chateau est en vue, a flanc de la montagne voisine. En commencant la descente, vous sentez le sol se dérober sous vos pieds. Vous atterissez plus bas sur un tas de neige. Vous etes dans une grotte immense. Un passage ouvragé se découpe au loin, surement une entrée cachée du chateau. Mais il y a aussi des lumières dans un autre passage, probablement une tribu de Kalte qui vit dans ces grottes. Vous pouvez allez leur demander de l'aide, dans ce cas allez les voir, ils portent un habit couleur kaki, sinon allez directement jusqu'à la porte du chateau",600)
        }
        prog = 4
        found = false
    }

    fun Etape4() {
        if (!CheckProg(4)) return
        found = true
        choix = 0
        ClearLayout()
        InstanceTextAt("Apres avoir passé la cour du chateau vous entrez dans une grande salle, avec une statue à l'air maléfique en son centre. Vous sentez qu'il ne faut pas la toucher. Il y a une porte sur le coté et un escalier au fond. Si vous maitrisez la telekinesie vous pouvez essayer de déplacer la statue.")
        InstanceImageAt("s1statueroom",640)
    }

    fun Etape4v(a:Int) {
        if (!CheckProg(4,5)) return

        ClearLayout()
        InstanceTextAt("Apres avoir passé la cour du chateau vous entrez dans une grande salle, avec une statue à l'air maléfique en son centre. Vous sentez qu'il ne faut pas la toucher. Il y a une porte sur le coté et un escalier au fond. Si vous maitrisez la telekinesie vous pouvez essayer de déplacer la statue.")
        InstanceImageAt("s1statueroom",640)

        when(a){
            0->InstanceTextAt("Vous vous avancez vers la porte ... ",1260)
            1->InstanceTextAt("Vous vous engagez dans l'escalier ... ",1260)
            2->if(skill==3) InstanceTextAt("Vous soulevez la pierre avec la force de votre mental. Une trappe secrète part de dessous. Vous vous y engagez",1260)else{ InstanceTextAt("Vous essayez de bouger la pierre par la pensée mais rien ne se passe",1260);return}
        }
        choix = a
        prog = 5
        found=false
    }

    fun Etape5() {
        if (!CheckProg(5,6)) return
        found=true
        ClearLayout()
        InstanceImageAt("s1tower")
        when(choix){
            0->{InstanceTextAt("Vous vous retrouvez dans une grande tour.\nAdmirez donc la vue. \n Le toit de la tour est en bois. Si seulement vous conaissiez un sort pour l'enflammer. Si vous en connaissez un vous pouvez l'ecrire, sinon vous pouvez continuer",600);InstanceButtonAt("Lancer un sort",1400,this::Etape5v,InstanceReaderAt("",1280).text)}
            1->InstanceTextAt("Vous descendez des escaliers, en montez d'autres. Vous arrivez en d'une tour de guet en pierre.\nAdmirez donc la vue. \nPuis vous repartez par un autre chemin",600)
            2->{InstanceTextAt("Apres être descendu puis montés un moment, vous arrivez en haut d'une tour de guet.\nAdmirez donc la vue.\n Son toit est en bois. L'un d'entre vous ayant sur lui un sort de feu,vous decidez d'enflammer la tour pour signaler votre position aux éventuels renforts qui arriveraient. Vous redescendez à tout allure et continuez dans une autre chemin.",600);skill=6}
        }
        prog = 6
        found = false

    }

    fun Etape5v(s:String)
    {
        if(s.toNormalCase()=="PYROMANCIO")
        {
            ClearLayout()
            InstanceImageAt("s1tower")
            InstanceTextAt("Vous vous retrouvez dans une grande tour. Son toit est en bois. Si seulement vous conaissiez un sort pour l'enflammer. Si vous en connaissez un vous pouvez l'ecrire, sinon vous pouvez continuer",600)
            InstanceTextAt("Le toit s'enflamme d'un coup. Vous sortez de la tour en courant et empruntez un autre chemin",1200)
            skill+=3;if(skill>10)skill=0
            prog = 6
            found = false
        }
    }
    fun Etape5r(){
        ClearLayout()
        InstanceImageAt("s1tower")
        if((skill>3 && skill !=9)||skill==0)
            InstanceTextAt("Apres être descendu puis montés un moment, vous arrivez en haut d'une tour de guet.\nAdmirez donc la vue.\n Son toit est en bois. L'un d'entre vous ayant sur lui un sort de feu,vous decidez d'enflammer la tour pour signaler votre position aux éventuels renforts qui arriveraient. Vous redescendez à tout allure et continuez dans une autre chemin.",600)
        else
            InstanceTextAt("Vous descendez des escaliers, en montez d'autres. Vous arrivez en d'une tour de guet en pierre.\nAdmirez donc la vue. \nPuis vous repartez par un autre chemin",600)
    }


    fun Etape6()
    {
        if (!CheckProg(6)) return
        found = true
        ClearLayout()
        InstanceTextAt("Vous arrivez dans les cachots du chateau. Ca a l'air d'un cul de sac, mais il y a quatres portes de cellules. Vous decidez d'en ouvrir une !")
        InstanceImageAt("s1cell",400)
        InstanceTextAt("Choisissez la porte que vous voulez ouvrir",1000)
    }

    fun Etape6v0()
    {
        if (!CheckProg(6,99)) return
        ClearLayout()
        prog=99
        jeu=9
        choix= 0
        InstanceTextAt("Un enorme monstre vous attends juste derriere la porte")
        InstanceImageAt("s1cell0",200)
        InstanceTextAt("Il a 10 points de vie. Vous devez vous défendre. Imaginez comment et mettez vos idées ici !",800)
        InstanceButtonAt("Action",1160,this::Etape6v0v,InstanceReaderAt("",1040).text)
    }

    fun Etape6v0v(sOld:String)
    {
        val s=sOld.toNormalCase()
        var ok = false
        if(s.contains("EPEE",true)) {
            InstanceTextAt("Vous lui mettez un coup d'épée : -3 pv",1300+choix*200);jeu-=3;choix++;ok = true }
        if(s.contains("POING",true)) {
            InstanceTextAt("Vous lui mettez un coup de poing : -1 pv",1300+choix*200);jeu-=1;choix++;ok = true }
        if(s.contains("BISOU",true)) {
            InstanceTextAt("Vous lui faites un bisou. C'est violent : -6 pv",1300+choix*200);jeu-=6;choix++;ok = true }
        if(s.contains("CALIN",true)) {
            InstanceTextAt("Vous lui faites un gros calin. Il est tout content : - 8pv",1300+choix*200);jeu-=8;choix++;ok = true }
        if(s.contains("COUTEAU",true)) {
            InstanceTextAt("Vous sortez un couteau. Il rit très fort : - 0pv",1300+choix*200);jeu-=0;choix++;ok = true }
        if(s.contains("SORT",true)) {
            InstanceTextAt("Vous essayez de jeter un sort. Ca ne marche pas, mais il se plie en 2 de rire : - 1pv",1300+choix*200);jeu-=1;choix++;ok = true }
        if(s.contains("HACHE",true)) {
            InstanceTextAt("Vous tentez un coup de hache, mais elle rebondit sur sa peau : - 1pv",1300+choix*200);jeu-=1;choix++;ok = true }
        if(s.contains("PIED",true)) {
            InstanceTextAt("Vous lui faites un croche-patte. Il s'étale de toute sa masse : - 3pv",1300+choix*200);jeu-=3;choix++;ok = true }
        if(s.contains("LIVRE",true)) {
            InstanceTextAt("Vous ouvrez un livre et lui racontez une histoire. Il s'endort comme un bébé : - 5pv",1300+choix*200);jeu-=5;choix++;ok = true }
        if(s.contains("COURIR",true)|| s.contains("fuir",true)) {
            InstanceTextAt("Vous tentez de fuir, mais il vous suit : - 0pv",1300+choix*200);jeu-=0;choix++;ok = true }
        if(s.contains("PEUR",true)) {
            InstanceTextAt("Vous lui racontez une histoire qui fait peur. Il a un peu peur : - 1pv",1300+choix*200);jeu-=1;choix++;ok = true }
        if(s.contains("COCHON",true)) {
            InstanceTextAt("Vous sortez un cochon, le faites griller à la broche et lui proposez un morceau. Il est ravi, c'est délicieux : - 10pv",1300+choix*200);jeu-=10;choix++;ok = true }

        if (!ok)
            ShowToast("Raté")
        else
            if(jeu<0)
            {
                DeleteAllOfTypeInLayout<Button>()
                DeleteAllOfTypeInLayout<EditText>()
                InstanceTextAt("Le monstre ne vous ennuira plus. Il n'y a rien d'autre ici. Vous ressortez et choisissez une autre porte",1480+choix*200)
                jeu=0
                prog=6
            }
    }


    fun Etape6v1()
    {
        if (!CheckProg(6,98)) return
        jeu = 0
        prog=98
        ClearLayout()
        InstanceTextAt("Vous arrivez dans une pièce au centre de laquelle se dresse une étrange machine. La porte se referme d'un coup derrière vous !")
        InstanceImageAt("s1cell1",320)
        InstanceButtonAt("Tirer le levier",940,this::Etape6v1v0)
        InstanceButtonAt("Appuyer sur le gros bouton",1060,this::Etape6v1v1)
    }
    fun Etape6v1v0(s:String)
    {
        jeu =0
        ClearLayout()
        animals.shuffle()
        InstanceTextAt("La machine se remet comme au début en crachant des jets de vapeurs")
        InstanceImageAt("s1cell1",300)
        InstanceButtonAt("Tirer le levier",940,this::Etape6v1v0)
        InstanceButtonAt("Appuyer sur le gros bouton",1060,this::Etape6v1v1)
    }
    fun Etape6v1v1(s:String)
    {
        ClearLayout()
        InstanceImageAt("s1cell1",300)
        InstanceButtonAt("Tirer le levier",940,this::Etape6v1v0)


        if (jeu>=10)
        {
            jeu = Random.nextInt(0, 9)
            InstanceTextAt("Quel est l'animal cité en " +(jeu+1).toString() +" ?")
            InstanceButtonAt("Appuyer sur le gros bouton",1060,this::Etape6v1v2,InstanceReaderAt("",160).text)
        }
        else{
            InstanceButtonAt("Appuyer sur le gros bouton",1060,this::Etape6v1v1)
            InstanceTextAt("La machine ecrit : ")
            InstanceTextAt(animals[jeu],100)
            jeu++
        }


    }
    fun Etape6v1v2(s:String)
    {
        if (s.toNormalCase()==animals[jeu])
        {
            ClearLayout()
            InstanceTextAt("La machine ecrit : BRAVO.")
            InstanceImageAt("s1cell1",300)
            InstanceTextAt("La porte s'ouvre. Vous êtes libre. Il n'y a rien d'autre ici. Vous pouvez allez choisir une autre porte.",900)
            prog=6
        }

    }

    fun Etape6v2()
    {
        if (!CheckProg(6)) return
        ClearLayout()
        InstanceTextAt("Vous entendez une voix derrière la porte. La voix demande s'il y a quelqu'un. Vous répondez, mais plus personne ne répond. Vous pouvez ouvrir la porte, ou alors aller tenter une autre porte")
        InstanceButtonAt("Ouvrir la porte",500,this::Etape6v2v)
    }

    fun Etape6v2v(s:String){
        ClearLayout()
        InstanceTextAt("Vous entendez une voix derrière la porte. La voix demande s'il y a quelqu'un. Vous répondez, mais plus personne ne répond. Vous pouvez ouvrir la porte, ou alors aller tenter une autre porte")
        InstanceImageAt("s1cell2",640)
        InstanceTextAt("Un vieil homme se trouve derrière la porte. En echange de la liberté, il est prêt à vous guider jusqu'au trésor du chateau. Vous le suivez donc !",1240)
        prog=7
        choix = 1
        jeu = 0
        found = false
    }

    fun Etape6v3()
    {
        if (!CheckProg(6)) return
        ClearLayout()
        InstanceTextAt("La porte s'ouvre sur une cellule immense, et completement vide. Il flotte encore une légère odeur de cochon grillé.")
        InstanceImageAt("s1cell3",320)
        InstanceTextAt("Rien à voir ici, vous revenez à l'entrée pour ouvrir une autre porte.",920)
    }

    fun Etape7()
    {
        if (!CheckProg(7)) return
        found = true
        ClearLayout()
        InstanceTextAt("Vous arrivez avec le vieux bonhomme jusqu'au pied du donjon. Vous le suivez jusqu'à l'intérieur")
        InstanceImageAt("s1dungeon0",280)
        InstanceTextAt("Tout à coup, vous entendez venant d'une porte sur le coté. En jetant un oeil vous voyez arriver un goupe de soldats armés jusqu'au dents, prêt à en découdre. Vous pouvez les attendre pour combattre, reculer prudement hors la tour, ou vous camoufler si vous maîtrisez cette technique",900)
        jeu =1
    }
    fun Etape7v0()
    {
        if(jeu==0)return
        prog=97
        ClearLayout()
        InstanceTextAt("3 créatures infernales vous tombent dessus")
        InstanceImageAt("s1monsters",190)
        InstanceTextAt("Pendant que quelques un d'entre vous font tournouyer leurs épées pour le tenir à distance, il faut préparer un sort pour les immobiliser.",800)
        InstanceImageAt("s1spell",1200,1000)
        InstanceButtonAt("Lancer sort",2320,this::Etape7v0v,InstanceReaderAt("",2200).text)

    }
    fun Etape7v0v(s:String){
        if(s.toNormalCase() =="LOSE MOMENTUM")
        {
            ClearLayout()
            InstanceTextAt("Vous avez immobilisé les trois monstres")
            InstanceImageAt("s1monsters",200)
            InstanceTextAt("Vous vous apercevez que le vieux a pris un coup mortel durant le combat. Il repose au sol, l'air tranquille. Le pauvre n'auras pas profité longtemps de la liberté. Vous empruntez maintenant l'escalier et montez dans le donjon.",800)
            choix =0
            prog=8
            found = false
        }
    }

    fun Etape7v1()
    {
        if(jeu==0)return
        ClearLayout()
        InstanceTextAt("Vous sortez du donjon en courant. Discrètement, vous observez alors l'arrivée de 3 créatures, qui se mettent à monter la garde.")
        InstanceImageAt("s1monsters",360)
        InstanceTextAt(" Vous allez devoir les affronter, à moins que vous ne parveniez à trouver un autre chemin",980)
    }
    fun Etape7v2()
    {
        if(skill != 1 && skill !=4) return
        if(jeu==0)return
        ClearLayout()
        InstanceTextAt("Votre camouflage est parfait et 3 créatures passent à deux mètres de vous sans vous voir.")
        InstanceImageAt("s1monsters",280)
        InstanceTextAt("Le vieux tremble, mais vous le tenez fermement pour qu'il ne fasse pas de bêtise. Après avoir attendu que le son de leur pas disparaisse au loin, vous entamez la montée du donjon.",900)
        prog=8
        found = false
    }
    fun Etape7v3()
    {
        if(jeu==0)return
        ClearLayout()
        InstanceTextAt("Bravo, vous avez trouvé un chemin sans monstres. Vous vous engagez dans les escaliers et montez dans le donjon")
        InstanceImageAt("s1dungeon0",320)
        prog=8
        found = false
    }
    fun Etape7r(){
        ClearLayout()
        InstanceImageAt("s1dungeon0")
        InstanceTextAt("Après avoir traversé le chateau, vous êtes arrivés au pied du donjon. Vous vous êtes ensuite engagé dans l'escalier et commencé votre ascension ...",600)
    }



    fun Etape8()
    {
        if (!CheckProg(8)) return
        found = true
        ClearLayout()
        InstanceTextAt("Après avoir monté pendant de longue minutes, vous arrivez dans un grand couloir enfumé.")
        InstanceImageAt("s1dungeon1",280)
        if(choix==1)
            InstanceTextAt("Deux issues dont situés d'un coté et de l'autre du couloir. Le vieux vous indique que les deux montent au sommet de la tour. Vous pouvez donc choisir n'impore lequel. Dans celui de gauche on entends des bruits de pas lointain. Si vous maitrisez le 6e sens vous pouvez l'utiliser.",900)
        else
            InstanceTextAt("Deux issues dont situés d'un coté et de l'autre du couloir. Dans celui de gauche on entends des bruits de pas lointain. Si vous maitrisez le 6e sens vous pouvez l'utiliser.",900)

        jeu =0
    }
    fun Etape8v0()//Gauche
    {
        if(jeu==1)return
        jeu = 3
        ClearLayout()
        InstanceImageAt("s1renfort",580)
        if(skill == 4 || skill == 5 || skill == 6) {
            InstanceTextAt("Vous montez les escaliers à gauche. Les pas sont de plus en plus fort. Enfin vous les apercevez. Il s'agit des renforts que vous avez appelés avec le feu sur la tour. Vous continuez votre route avec un groupe largement plus nombreux.")
            if(choix==1)InstanceTextAt("Pendant la bousculade qui a suivi la retrouvaille, il semble que le vieil homme se soit fait la malle. Tant pis, vous allez devoir continuer sans lui",1200)
            choix = 0
            prog = 9
            jeu = 0
            found = false
        }
        else{
            InstanceTextAt("Vous montez les escaliers à gauche. Les pas sont de plus en plus fort. Enfin vous les apercevez. Il s'agit de grands guerriers revètus d'armure et lourdement armés. Vous allez devoir vous défendre.");InstanceTextAt ("Vous allez A TOUR DE ROLE devoir lancer un sort très puissant qui nécéssite beaucoup de bruit. Vous avez 6 essais. Attaquez après avoir fait du bruit !",1200)
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
                mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
                mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                mRecorder.setOutputFile("/dev/null")
                mRecorder.prepare()
                mRecorder.start()
            }else
            {
                InstanceTextAt("Attention, l'application n'a pas l'autorisation d'acceder au microphone. Le mini-jeu ici est désactivé. Allez mettre l'autorisation, ou appuyez juste sur attaque pour continuer",1660)
            }

            InstanceButtonAt("Attaquer",1580,this::Etape8v0v)
        }
    }
    fun Etape8v0v(s:String)
    {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            InstanceTextAt("Guerrier "+(jeu-2).toString()+" : Amplitude "+mRecorder.maxAmplitude.toString(),1740+(jeu-2)*100)}
        if(jeu<8)
            jeu++
        else{
            if(choix==0)
                InstanceTextAt("Hourra, les guerriers sont à terre ! Vous montez vers le sommet.",2440)
            else
                InstanceTextAt("Hourra, les guerriers sont à terre ! Mais il semblerai que le vieil homme se soit enfuit pendant la bataille. Tant pis, vous continuez vers le sommet.",2440)

            if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
                mRecorder.stop()
            }
            DeleteAllOfTypeInLayout<Button>()
            choix = 0
            prog = 9
            found = false
        }
    }
    fun Etape8v1()
    {
        if(jeu==1)return
        if(skill == 4 || skill == 5 || skill == 6)
            skill-=3
        ClearLayout()
        InstanceTextAt("Vous vous engagez dans l'escalier de droite. Mais tout en haut, une porte fermée bloque la voie, avec une image d'oreille déssinée dessus. Il faut probablement lui dire quelque chose. Un étrange mur se trouve tout à coté :")
        InstanceImageAt("s1imagemot",600)
        InstanceButtonAt("Dire",1300,this::Etape8v1v,InstanceReaderAt("",1180).text)

        InstanceTextAt("(Vous pouvez reflechir en marchant, la carte suivante est quand meme débloquée)",1600)
        jeu = 2
    }
    fun Etape8v1v(s:String){
        if(s.toNormalCase()=="COQ")
        {
            ClearLayout()
            InstanceTextAt("Bravo, la porte s'ouvre. Vous pouvez la franchir.")
            jeu=0
            prog = 9
            found = false
        }
    }

    fun Etape8v2()
    {
        if(jeu==1)return
        if(skill != 2 && skill !=5) return
        ClearLayout()
        if(skill == 2)
            InstanceTextAt("Votre 6e sens vous prévient qu'il s'agit d'une grosse escouade d'hommes en armes, avec des intentions hostiles. Vous remerciez le ciel d'avoir cette capacité et prenez l'autre chemin.")
        else
            {InstanceTextAt("Votre 6e sens vous prévient qu'il s'agit d'une grosse escouade d'hommes en armes, mais également que vous les connaissez. Vous montez les escaliers à leur rencontre.");InstanceTextAt( "Il s'agit des renforts que vous avez appelés avec le feu sur la tour. Vous continuez votre route avec un groupe largement plus nombreux.",1120)}
        InstanceImageAt("s1renfort",500)
        prog = 9
        found = false
    }
    fun Etape8r(){
        ClearLayout()
        InstanceImageAt("s1dungeon1")
        InstanceTextAt("Vous montez toujours dans la tour ...",600)
    }
    fun Etape9()
    {
        if (!CheckProg(9)) return
        found = true
        ClearLayout()
        if(choix==1){
            InstanceTextAt("Le vieil homme s'avance et vous dit 'Comme promis, je vais vous montrer ou se trouve le trésor'.\n Il s'approche d'un mur, et appuie dessus. Le mur coulisse. ")
            InstanceImageAt("s1treasure",400)
            InstanceTextAt("Puis il vous montre une porte immense : 'Le sorcier se trouve là !'",1020)
            InstanceImageAt("s1bossgate",1200,1500)
            InstanceTextAt("La porte est fermée. Il y a cinq vers écrits sur le fronton : \n\nSans voix , il hurle,\nSans ailes , il voltige,\nSans dents , il mord\nSans bouche , il murmure,\nSans main, il caresse.",2710)
            InstanceButtonAt("Dire",3420,this::Etape9v,InstanceReaderAt("",3300).text)
        }
        else
        {
            InstanceTextAt("Vous arrivez tout en haut du donjon. Il s'agit d'une immense salle, dont les murs sont recouvers de tapisseries. Vous sondez ces dernières dans l'espoir de trouver une salle secrète, en vain. Vous faisant face se trouve une porte immense . ")
            InstanceImageAt("s1bossgate",600,1800)
            InstanceTextAt("La porte est fermée. Il y a cinq vers écrits sur le fronton : \n\nSans voix , il hurle,\nSans ailes , il voltige,\nSans dents , il mord\nSans bouche , il murmure,\nSans main, il caresse.",2420)
            InstanceButtonAt("Dire",3120,this::Etape9v,InstanceReaderAt("",3000).text)
        }


    }
    fun Etape9v(s:String)
    {
        if(s.toNormalCase()=="VENT" || s.toNormalCase() == "LE VENT") {
            DeleteAllOfTypeInLayout<Button>()
            DeleteAllOfTypeInLayout<EditText>()
            InstanceTextAt("La porte s'ouvre lentement. Vous vous en approchez, doucement ...",3360 )
            prog=10
            found = false
        }
    }
    fun Etape9r(){
        ClearLayout()
        InstanceImageAt("s1bossgate",0,1800)
        InstanceTextAt("Après être arrivé toute en haut de la tour,vous arrivez en face d'une porte immense. Celle ci s'ouvre lentement. Vous vous en approchez, doucement ...",1820)
    }


    fun Etape10()
    {
        if (!CheckProg(10)) return
        found = true
        jeu = 0
        ClearLayout()
        InstanceTextAt("Vous entrez dans une grande salle. Le sorcier est la, en train d'ouvrir un portail vers une autre dimension. Il se retourne et ricane : 'Haha ! Vous avez du avoir du mal à arriver jusqu'ici, mais c'est en vain ! Vous allez périr ici'")
        InstanceImageAt("s1boss0",580)
        if(skill==4||skill==5||skill==6 || skill == 0)
            InstanceTextAt("Il est très puissant. Il s'attaquera à vous en lancant des répliques de films. Si vous répondez avec le titre du film, il sera déstabilisé et vous pourrez lui mettre un coup. Il est très fort, mais avec les renforts vous êtes nombreux, il ne vous faudras que 8 coups pour le vaincre. Si vous ne savez vraiment pas, vous pouvez utiliser votre pouvoir pour vous protéger. ",1200)
        else
            InstanceTextAt("Il est très puissant. Il s'attaquera à vous en lancant des répliques de films. Si vous répondez avec le titre du film, il sera déstabilisé et vous pourrez lui mettre un coup. Il est très fort, il faudra lui mettre 16 coups pour le vaincre. Si vous ne savez vraiment pas, vous pouvez utiliser votre pouvoir pour vous protéger. ",1200)
        InstanceButtonAt("Commencer le combat !",1900,this::Etape10v1)
        //ShowToast(films.size.toString()+ " : " + filmsbool.size.toString() +" : "+ repliques.size.toString())

    }

    fun Etape10v0(s:String){
        if(s.toNormalCase() != films[jeu])return
        filmsbool[jeu] = true
        jeu++
        if ((filmsbool.sum() == 8 && (skill==4||skill==5||skill==6 || skill == 0)) || filmsbool.sum() == 16 )
            Etape10r()
        else
            Etape10v1("")
    }

    fun Etape10v1(s:String) {
        ClearLayout()
        InstanceTextAt("Vous entrez dans une grande salle. Le sorcier est la, en train d'ouvrir un portail vers une autre dimension. Il se retourne et ricane : 'Haha ! Vous avez du avoir du mal à arriver jusqu'ici, mais c'est en vain ! Vous allez périr ici'")
        InstanceImageAt("s1boss0",560)
        var compt=0
        for(i in 0..films.size-1)
            if(filmsbool[i]){
                InstanceTextAt(films[i],1840+compt*100)
                compt++
            }

        ChooseNextFilm()
        InstanceTextAt(repliques[jeu],1180)
        InstanceButtonAt("Répondre",1580,this::Etape10v0,InstanceReaderAt("",1460).text)
        when(skill){
            1,4->InstanceButtonAt("Se camoufler",1700,this::Etape10v1)
            2,5->InstanceButtonAt("Anticiper",1700,this::Etape10v1)
            3,6->InstanceButtonAt("Télékinésiter",1700,this::Etape10v1)
        }
    }

    fun Etape10r(){
        ClearLayout()
        InstanceTextAt("Vous entrez dans une grande salle. Le sorcier est la, en train d'ouvrir un portail vers une autre dimension. Il se retourne et ricane : 'Haha ! Vous avez du avoir du mal à arriver jusqu'ici, mais c'est en vain ! Vous allez périr ici'")
        InstanceImageAt("s1boss0",580)
        InstanceTextAt("Vous avez livré un intense combat, mais ca y est, vous avez gagné. Le sorcier est tombé. Triomphant, vous lancez un dernier sort vers le portail, qui explose dans un bruit assourdissant. Il ne vous reste plus qu'à revenir à l'entrée du chateau pour clore cette aventure.",1200)
        prog =11
        found = false
    }

    private fun ChooseNextFilm(){
        var i= 0
        jeu = Random.nextInt(0, films.size)
        while(jeu != 0)
        {
            i++
            if(i>=filmsbool.size)
                i=0
            if(!filmsbool[i])
                jeu --
        }
        jeu = i
    }


        fun Etape11(){
        if (!CheckProg(11)) return
        found = true
        ClearLayout()
        InstanceImageAt("s1end")
        if(choix == 0)
        {
            InstanceTextAt("C'est une victoire ! Le monde est libéré de ce sorcier ! Longtemps on parlera de cette exploit que vous avez accompli. On se racontera cette aventure au coin du feu, on la jouera sur scene, on l'écrira et on la chantera.\nA bientôt pour d'autres aventures",600)
        }
        else
        {
            InstanceTextAt("Quelle incroyable victoire ! Vous avez terrassé le sorcier et récuperer son trésor. C'est un exploit dont on parlera longtemps !",600)
            InstanceImageAt("s1treasure",960)
            InstanceTextAt("C'est la gloire et la richesse pour de nombreuses années ! \nA bientôt pour d'autres aventures",1560)
        }
    }


    override fun Reset() {
        prog = 0
        skill=0
        choix=0
        jeu=0
        found=false
        for (e in 0..filmsbool.size)
            filmsbool[e]=false
    }
}