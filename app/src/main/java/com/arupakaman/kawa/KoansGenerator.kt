package com.arupakaman.kawa

import android.content.Context
import android.util.Log
import com.arupakaman.kawa.data.database.entities.Koan
import org.json.JSONArray
import org.json.JSONObject
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStreamWriter
import java.io.Writer

class KoansGenerator(val context: Context) {

    fun prepareJsonArray(){
        val arrKoans= getArrayList()

        Log.d("arrKoans, size:",arrKoans.size.toString())

        Log.d("arrKoans set, size:",arrKoans.toSet().size.toString())

        JSONArray().apply {
            arrKoans.forEach {koan->
                JSONObject().apply {
                    put("title",koan.title)
                    put("koan",koan.koan)
                 //   put("image",koan.koanImagePath)
                }.also {
                    put(it)
                }
            }
        }.also {jar->

            saveRespIntoFile(context=context,mytext = jar.toString())
        }
    }

    private fun saveRespIntoFile(fileName:String="jsonData", context: Context, mytext: String?): Boolean {

        return try {
            val fos: FileOutputStream =
                context.openFileOutput("$fileName.txt", Context.MODE_PRIVATE)
            val out: Writer = OutputStreamWriter(fos)
            out.write(mytext)
            out.close()
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    val arrKoans=ArrayList<Koan>()

    fun addNewKoan(koanData: Koan.()->Unit){
        val koan = Koan()
        koan.koanData()
        arrKoans.add(koan)
    }

    private fun getArrayList(): ArrayList<Koan> {

        addNewKoan {
            title="A Cup of Tea"
            koan="""
                   <p>Nan-in, a Japanese master during the Meiji era (1868-1912), received a university professor who came to inquire about Zen.</p>

                   <p>Nan-in served tea. He poured his visitor&#39;s cup full, and then kept on pouring.</p>

                   <p>The professor watched the overflow until he no longer could restrain himself. &quot;It is overfull. No more will go in!&quot;</p>

                   <p>&quot;Like this cup,&quot; Nan-in said, &quot;you are full of your own opinions and speculations. How can I show you Zen unless you first empty your cup?&quot;</p>

                   <h2>&nbsp;</h2>
                """.trimIndent()
            //koanImagePath=""
        }


        addNewKoan {
            title="Finding a Diamond on a Muddy Road"
            koan="""
                    <p>Gudo was the emperor&#39;s teacher of his time. Nevertheless, he used to travel alone as a wandering mendicant. Once when he was on his way to Edo, the cultural and political center of the shogunate, he approached a little village named Takenaka. It was evening and a heavy rain was falling. Gudo was thoroughly wet. His straw sandals were in pieces. At a farmhouse near the village he noticed four or five pairs of sandals in the window and decided to buy some dry ones.</p>

                    <p>The woman who offered him the sandals, seeing how wet he was, invited him in to remain for the night in her home. Gudo accepted, thanking her. He entered and recited a sutra before the family shrine. He was then introduced to the women&#39;s mother, and to her children. Observing that the entire family was depressed, Gudo asked what was wrong.</p>

                    <p>&quot;My husband is a gambler and a drunkard,&quot; the housewife told him. &quot;When he happens to win he drinks and becomes abusive. When he loses he borrows money from others. Sometimes when he becomes thoroughly drunk he does not come home at all. What can I do?&quot;</p>

                    <p>&quot;I will help him,&quot; said Gudo. &quot;Here is some money. Get me a gallon of fine wine and something good to eat. Then you may retire. I will meditate before the shrine.&quot;</p>

                    <p>When the man of the house returned about midnight, quite drunk, he bellowed: &quot;Hey, wife, I am home. Have you something for me to eat?&quot;</p>

                    <p>&quot;I have something for you,&quot; said Gudo. &quot;I happened to be caught in the rain and your wife kindly asked me to remain here for the night. In return I have bought some wine and fish, so you might as well have them.&quot;</p>

                    <p>The man was delighted. He drank the wine at once and laid himself down on the floor. Gudo sat in meditation beside him.</p>

                    <p>In the morning when the husband awoke he had forgotten about the previous night. &quot;Who are you? Where do you come from?&quot; he asked Gudo, who was still meditating.</p>

                    <p>&quot;I am Gudo of Kyoto and I am going on to Edo,&quot; replied the Zen master.</p>

                    <p>The man was utterly ashamed. He apologized profusely to the teacher of his emperor.</p>

                    <p>Gudo smiled. &quot;Everything in this life is impermanent,&quot; he explained. &quot;Life is very brief. If you keep on gambling and drinking, you will have no time left to accomplish anything else, and you will cause your family to suffer too.&quot;</p>

                    <p>The perception of the husband awoke as if from a dream. &quot;You are right,&quot; he declared. &quot;How can I ever repay you for this wonderful teaching! Let me see you off and carry your things a little way.&quot;</p>

                    <p>&quot;If you wish,&quot; assented Gudo.</p>

                    <p>The two started out. After they had gone three miles Gudo told him to return. &quot;Just another five miles,&quot; he begged Gudo. They continued on.</p>

                    <p>&quot;You may return now,&quot; suggested Gudo.</p>

                    <p>&quot;After another ten miles,&quot; the man replied.</p>

                    <p>&quot;Return now,&quot; said Gudo, when the ten miles had been passed.</p>

                    <p>&quot;I am going to follow you all the rest of my life,&quot; declared the man.</p>

                    <p>Modern Zen teachings in Japan spring from the lineage of a famous master who was the successor of Gudo. His name was Mu-nan, the man who never turned back.</p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>
                                                
                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Is That So?"
            koan="""
                <p>The Zen master Hakuin was praised by his neighbours as one living a pure life.</p>

                <p>A beautiful Japanese girl whose parents owned a food store lived near him. Suddenly, without any warning, her parents discovered she was with child.</p>

                <p>This made her parents angry. She would not confess who the man was, but after much harassment at last named Hakuin.</p>

                <p>In great anger the parent went to the master. &quot;Is that so?&quot; was all he would say.</p>

                <p>After the child was born it was brought to Hakuin. By this time he had lost his reputation, which did not trouble him, but he took very good care of the child. He obtained milk from his neighbours and everything else he needed.</p>

                <p>A year later the girl-mother could stand it no longer. She told her parents the truth - the real father of the child was a young man who worked in the fishmarket.</p>

                <p>The mother and father of the girl at once went to Hakuin to ask forgiveness, to apologize at length, and to get the child back.</p>

                <p>Hakuin was willing. In yielding the child, all he said was: &quot;Is that so?&quot;</p>

                <h2>&nbsp;</h2>

            """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Obedience"
            koan="""
                    <p>The master Bankei&#39;s talks were attended not only by Zen students but by persons of all ranks and sects. He never quoted sutras not indulged in scholastic dissertations. Instead, his words were spoken directly from his heart to the hearts of his listeners.</p>

                    <p>His large audience angered a priest of the Nichiren sect because the adherents had left to hear about Zen. The self-centered Nichiren priest came to the temple, determined to have a debate with Bankei.</p>

                    <p>&quot;Hey, Zen teacher!&quot; he called out. &quot;Wait a minute. Whoever respects you will obey what you say, but a man like myself does not respect you. Can you make me obey you?&quot;</p>

                    <p>&quot;Come up beside me and I will show you,&quot; said Bankei.</p>

                    <p>Proudly the priest pushed his way through the crowd to the teacher.</p>

                    <p>Bankei smiled. &quot;Come over to my left side.&quot;</p>

                    <p>The priest obeyed.</p>

                    <p>&quot;No,&quot; said Bankei, &quot;we may talk better if you are on the right side. Step over here.&quot;</p>

                    <p>The priest proudly stepped over to the right.</p>

                    <p>&quot;You see,&quot; observed Bankei, &quot;you are obeying me and I think you are a very gentle person. Now sit down and listen.&quot;</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="If You Love, Love Openly"
            koan="""
                    <p>Twenty monks and one nun, who was named Eshun, were practicing meditation with a certain Zen master.</p>

                    <p>Eshun was very pretty even though her head was shaved and her dress plain. Several monks secretly fell in love with her. One of them wrote her a love letter, insisting upon a private meeting.</p>

                    <p>Eshun did not reply. The following day the master gave a lecture to the group, and when it was over, Eshun arose. Addressing the one who had written to her, she said: &quot;If you really love me so much, come and embrace me now.&quot;</p>

                    <h2>&nbsp;</h2>
                    
                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="No Loving-Kindness"
            koan="""
                    <p>There was an old woman in China who had supported a monk for over twenty years. She had built a little hut for him and fed him while he was meditating. Finally she wondered just what progress he had made in all this time.</p>

                    <p>To find out, she obtained the help of a girl rich in desire. &quot;Go and embrace him,&quot; she told her, &quot;and then ask him suddenly: &#39;What now?&#39;&quot;</p>

                    <p>The girl called upon the monk and without much ado caressed him, asking him what he was going to do about it.</p>

                    <p>&quot;An old tree grows on a cold rock in winter,&quot; replied the monk somewhat poetically. &quot;Nowhere is there any warmth.&quot;</p>

                    <p>The girl returned and related what he had said.</p>

                    <p>&quot;To think I fed that fellow for twenty years!&quot; exclaimed the old woman in anger. &quot;He showed no consideration for your needs, no disposition to explain your condition. He need not have responded to passion, but at least he should have evidenced some compassion.&quot;</p>

                    <p>She at once went to the hut of the monk and burned it down.</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Announcement"
            koan="""
                    <p>Tanzan wrote sixty postal cards on the last day of his life, and asked an attendent to mail them. Then he passed away.</p>

                    <p>The cards read:</p>

                    <p><em>I am departing from this world.<br />
                    This is my last announcement.<br />
                    Tanzan<br />
                    July 27, 1892</em></p>

                    <h2>&nbsp;</h2>
                                        
                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Great Waves"
            koan="""
                    <p>In the early days of the Meiji era there lived a well-known wrestler called O-nami, Great Waves.</p>

                    <p>O-nami was immensely strong and knew the art of wrestling. In his private bouts he defeated even his teacher, but in public he was so bashful that his own pupils threw him.</p>

                    <p>O-nami felt he should go to a Zen master for help. Hakuju, a wandering teacher, was stopping in a little temple nearby, so O-nami went to see him and told him of his trouble.</p>

                    <p>&quot;Great Waves is your name,&quot; the teacher advised, &quot;so stay in this temple tonight. Imagine that you are those billows. You are no longer a wrestler who is afraid. You are those huge waves sweeping everything before them, swallowing all in their path. Do this and you will be the greatest wrestler in the land.&quot;</p>

                    <p>The teacher retired. O-nami sat in meditation trying to imagine himself as waves. He thought of many different things. Then gradually he turned more and more to the feeling of the waves. As the night advanced the waves became larger and larger. They swept away the flowers in their vases. Even the Buddha in the shrine was inundated. Before dawn the temple was nothing but the ebb and flow of an immense sea.</p>

                    <p>In the morning the teacher found O-nami meditating, a faint smile on his face. He patted the wrestler&#39;s shoulder. &quot;Now nothing can disturb you,&quot; he said. &quot;You are those waves. You will sweep everything before you.&quot;</p>

                    <p>The same day O-nami entered the wrestling contests and won. After that, no one in Japan was able to defeat him.</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="The Moon Cannot Be Stolen"
            koan="""
                    <p>Ryokan, a Zen master, lived the simplest kind of life in a little hut at the foot of a mountain. One evening a thief visited the hut only to discover there was nothing to steal.</p>

                    <p>Ryokan returned and caught him. &quot;You have come a long way to visit me,&quot; he told the prowler, &quot;and you should not return empty-handed. Please take my clothes as a gift.&quot;</p>

                    <p>The thief was bewildered. He took the clothes and slunk away.</p>

                    <p>Ryoken sat naked, watching the moon. &quot;Poor fellow,&quot; he mused, &quot;I wish I could have given him this beautiful moon.&quot;</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="The Last Poem of Hoshin"
            koan="""
                    <p>The Zen Master Hoshin lived in China many years. Then he returned to the northeastern part of Japan, where he taught his disciples. When he was getting very old, he told them a story he had heard in China. This is the story:</p>

                    <p>One year on the twenty-fifth of December, Tokufu, who was very old, said to his disciples: &quot;I am not going to be alive next year so you fellows should treat me well this year.&quot;</p>

                    <p>The pupils thought he was joking, but since he was a great-hearted teacher each of them in turn treated him to a feast on succeeding days of the departing year.</p>

                    <p>On the eve of the new year, Tokufu concluded: &quot;You have been good to me. I shall leave tomorrow afternoon when the snow has stopped.&quot;</p>

                    <p>The disciples laughed, thinking he was aging and talking nonsense since the night was clear and without snow. But at midnight snow began to fall, and the next day they did not find their teacher about. They went to the meditation hall. There he had passed on.</p>

                    <p>Hoshin, who related this story, told his disciples: &quot;It is not necessary for a Zen master to predict his passing, but if he really wishes to do so, he can.&quot;</p>

                    <p>&quot;Can you?&quot; someone asked.</p>

                    <p>&quot;Yes,&quot; answered Hoshin. &quot;I will show you what I can do seven days from now.&quot;</p>

                    <p>None of the disciples believed him, and most of them had even forgotten the conversation when Hoshin called them together.</p>

                    <p>&quot;Seven days ago,&quot; he remarked, &quot;I said I was going to leave you. It is customary to write a farewell poem, but I am neither a poet or a calligrapher. Let one of you inscribe my last words.&quot;</p>

                    <p>His followers thought he was joking, but one of them started to write.</p>

                    <p>&quot;Are you ready?&quot; Hoshin asked.</p>

                    <p>&quot;Yes sir,&quot; replied the writer.</p>

                    <p>Then Hoshin dictated:</p>

                    <p><em>I came from brillancy<br />
                    And return to brillancy.<br />
                    What is this? </em></p>

                    <p>This line was one line short of the customary four, so the disciple said: &quot;Master, we are one line short.&quot;</p>

                    <p>Hoshin, with the roar of a conquering lion, shouted &quot;Kaa!&quot; and was gone.</p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="The Story of Shunkai"
            koan="""
                    <p>The exquisite Shunkai whose other name was Suzu was compelled to marry against her wishes when she was quite young. Later, after this marriage had ended, she attended the university, where she studied philosophy.</p>

                    <p>To see Shunkai was to fall in love with her. Moreover, wherever she went, she herself fell in love with others. Love was with her at the university, and afterwards when philosophy did not satisfy her and she visited the temple to learn about Zen, the Zen students fell in love with her. Shunkai&#39;s whole life was saturated with love.</p>

                    <p>At last in Kyoto she became a real student of Zen. Her brothers in the sub-temple of Kennin praised her sincerity. One of them proved to be a congenial spirit and assisted her in the mastery of Zen.</p>

                    <p>The abbot of Kennin, Mokurai, Silent Thunder, was severe. He kept the precepts himself and expected the priests to do so. In modern Japan whatever zeal these priests have lost for Buddhism they seemed to have gained for having wives. Mokurai used to take a broom and chase the women away when he found them in any of his temples, but the more wives he swept out, the more seemed to come back.</p>

                    <p>In this particular temple the wife of the head priest had become jealous of Shunkai&#39;s earnestness and beauty. Hearing the students praise her serious Zen made this wife squirm and itch. Finally she spread a rumor about that Shunkai and the young man who was her friend. As a consequence he was expelled and Shunkai was removed from the temple.</p>

                    <p>&quot;I may have made the mistake of love,&quot; thought Shunkai, &quot;but the priest&#39;s wife shall not remain in the temple either if my friend is to be treated so unjustly.&quot;</p>

                    <p>Shunkai the same night with a can of kerosene set fire to the five-hundred-year-old temple and burned it to the ground. In the morning she found herself in the hands of the police.</p>

                    <p>A young lawyer became interested in her and endeavoured to make her sentance lighter. &quot;Do not help me.&quot; she told him. &quot;I might decide to do something else which will only imprison me again.&quot;</p>

                    <p>At last a sentance of seven years was completed, and Shunkai was released from the prison, where the sixty-year-old warden also had become enamored of her.</p>

                    <p>But now everyone looked upon her as a &quot;jailbird&quot;. No one would associate with her. Even the Zen people, who are supposed to believe in enlightenment in this life and with this body, shunned her. Zen, Shunkai found, was one thing and the followers of Zen quite another. Her relatives would have nothing to do with her. She grew sick, poor, and weak.</p>

                    <p>She met a Shinshu priest who taught her the name of the Buddha of Love, and in this Shunkai found some solace and peace of mind. She passed away when she was still exquisitely beautiful and hardly thirty years old.</p>

                    <p>She wrote her own story in a futile endeavour to support herself and some of it she told to a women writer. So it reached the Japanese people. Those who rejected Shunkai, those who slandered and hated her, now read of her life with tears of remorse.</p>

                    <p>&nbsp;</p>

                    <h2>&nbsp;</h2>


                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Happy Chinaman"
            koan="""
                    <p>Anyone walking about Chinatowns in America with observe statues of a stout fellow carrying a linen sack. Chinese merchants call him Happy Chinaman or Laughing Buddha.</p>

                    <p>This Hotei lived in the T&#39;ang dynasty. He had no desire to call himself a Zen master or to gather many disciples about him. Instead he walked the streets with a big sack into which he would put gifts of candy, fruit, or doughnuts. These he would give to children who gathered around him in play. He established a kindergarten of the streets.</p>

                    <p>Whenever he met a Zen devotee he would extend his hand and say: &quot;Give me one penny.&quot; And if anyone asked him to return to a temple to teach others, again he would reply: &quot;Give me one penny.&quot;</p>

                    <p>Once he was about his play-work another Zen master happened along and inquired: &quot;What is the significance of Zen?&quot;</p>

                    <p>Hotei immediately plopped his sack down on the ground in silent answer.</p>

                    <p>&quot;Then,&quot; asked the other, &quot;what is the actualization of Zen?&quot;</p>

                    <p>At once the Happy Chinaman swung the sack over his shoulder and continued on his way.</p>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="A Buddha"
            koan="""
                    <p>In Tokyo in the Meiji era there lived two prominent teachers of opposite characteristics. One, Unsho, an instructor in Shingon, kept Buddha&#39;s precepts scrupulously. He never drank intoxicants, nor did he eat after eleven o&#39;clock in the morning. The other teacher, Tanzan, a professor of philosophy at the Imperial University, never observed the precepts. Whenever he felt like eating, he ate, and when he felt like sleeping in the daytime he slept.</p>

                    <p>One day Unsho visited Tanzan, who was drinking wine at the time, not even a drop of which is suppposed to touch the tongue of a Buddhist.</p>

                    <p>&quot;Hello, brother,&quot; Tanzan greeted him. &quot;Won&#39;t you have a drink?&quot;</p>

                    <p>&quot;I never drink!&quot; exclaimed Unsho solemnly.</p>

                    <p>&quot;One who does not drink is not even human,&quot; said Tanzan.</p>

                    <p>&quot;Do you mean to call me inhuman just because I do not indulge in intoxicating liquids!&quot; exclaimed Unsho in anger. &quot;Then if I am not human, what am I?&quot;</p>

                    <p>&quot;A Buddha,&quot; answered Tanzan.</p>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Muddy Road"
            koan="""
                <p>Tanzan and Ekido were once traveling together down a muddy road. A heavy rain was still falling.</p>

                <p>Coming around a bend, they met a lovely girl in a silk kimono and sash, unable to cross the intersection.</p>

                <p>&quot;Come on, girl&quot; said Tanzan at once. Lifting her in his arms, he carried her over the mud.</p>

                <p>Ekido did not speak again until that night when they reached a lodging temple. Then he no longer could restrain himself. &quot;We monks don&#39;t go near females,&quot; he told Tanzan, &quot;especially not young and lovely ones. It is dangerous. Why did you do that?&quot;</p>

                <p>&quot;I left the girl there,&quot; said Tanzan. &quot;Are you still carrying her?&quot;</p>

                <h2>&nbsp;</h2>

            """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Shoun & His Mother"
            koan="""
                    <p>Shoun became a teacher of Soto Zen. When he was still a student his father passed away, leaving him to care for his old mother.</p>

                    <p>Whenever Shoun went to a meditation hall he always took his mother with him. Since she accompanied him, when he visited monasteries he could not live with the monks. So he would built a little house and care for her there. He would copy sutras, Buddhist verses, and in this manner receive a few coins for food.</p>

                    <p>When Shoun bought fish for his mother, the people would scoff at him, fo a monk is not supposed to eat fish. But Shoun did not mind. His mother, however, was hurt to see others laugh at her son. Finally she told Shoun: &quot;I think I will become a nun. I can be vegetarian too.&quot; She did, and they studied together.</p>

                    <p>Shoun was fond of music and was a master of the harp, which his mother also played. On full-moon nights they used to play together. One night a young lady passed by their house and heard music. Deeply touched, she invited Shoun to visit her the next evening and play. He accepted the invitation. A few days later he met the young lady on the street and thanked her for her hospitality. Others laughed at him. He had visited the house of a woman of the streets.</p>

                    <p>One day Shoun left for a distant temple to deliver a lecture. A few months afterwards he returned home to find his mother dead. Friends had not known where to reach him, so the funeral was in progress.</p>

                    <p>Shoun walked up and hit the coffin with his staff. &quot;Mother, your son has returned,&quot; he said.</p>

                    <p>&quot;I am glad to see you have returned, son,&quot; he answered for his mother.</p>

                    <p>&quot;Yes, I am glad too,&quot; Shoun responded. Then he announced to the people about him: &quot;The funeral ceremony is over. You may bury the body.&quot;</p>

                    <p>When Shoun was old he knew his end was approaching. He asked his disciples to gather around him in the morning, telling them he was going to pass on at noon. Burning incense before the picture of his mother and his old teacher, he wrote a poem:</p>

                    <p><em>For fifty-six years I lived as best I could,<br />
                    Making my way in this world.<br />
                    Now the rain has ended, the clouds are clearing,<br />
                    The blue sky has a full moon.</em></p>

                    <p>His disciples gathered around him, reciting sutra, and Shoun passed on during the invocation.</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Not Far from Buddhahood"
            koan="""
                    <p>A university student while visiting Gasan asked him: &quot;Have you ever read the Christian Bible?&quot;</p>

                    <p>&quot;No, read it to me,&quot; said Gasan.</p>

                    <p>The student opened the Bible and read from St. Matthew: &quot;And why take ye thought for rainment? Consider the lilies of the field, how they grow. They toil not, neither do they spin, and yet I say unto you that even Solomon in all his glory was not arrayed like one of these... Take therefore no thought for the morrow, for the morrow shall take thought for the things of itself.&quot;</p>

                    <p>Gasan said: &quot;Whoever uttered those words I consider an enlightened man.&quot;</p>

                    <p>The student continued reading: &quot;Ask and it shall be given you, seek and ye shall find, knock and it shall be opened unto you. For everyone that asketh receiveth, and he that seeketh findeth, and to him that knocketh, it shall be opened.&quot;</p>

                    <p>Gasan remarked: &quot;That is excellent. Whoever said that is not far from Buddhahood.&quot;</p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Stingy in Teaching"
            koan="""
                    <p>A young physician in Tokyo named Kusuda met a college friend who had been studying Zen. The young doctor asked him what Zen was.</p>

                    <p>&quot;I cannot tell you what it is,&quot; the friend replied, &quot;but one thing is certain. If you understand Zen, you will not be afraid to die.&quot;</p>

                    <p>&quot;That&#39;s fine,&quot; said Kusuda. &quot;I will try it. Where can I find a teacher?&quot;</p>

                    <p>&quot;Go to the master Nan-in,&quot; the friend told him.</p>

                    <p>So Kusuda went to call on Nan-in. He carried a dagger nine and a half inches long to determine whether or not the teacher was afraid to die.</p>

                    <p>When Nan-in saw Kusuda he exclaimed: &quot;Hello, friend. How are you? We haven&#39;t seen each other for a long time!&quot;</p>

                    <p>This perplexed Kusuda, who replied: &quot;We have never met before.&quot;</p>

                    <p>&quot;That&#39;s right,&quot; answered Nan-in. &quot;I mistook you for another physician who is receiving instruction here.&quot;</p>

                    <p>With such a begining, Kusuda lost his chance to test the master, so reluctantly he asked if he might receive instruction.</p>

                    <p>Nan-in said: &quot;Zen is not a difficult task. If you are a physician, treat your patients with kindness. That is Zen.&quot;</p>

                    <p>Kusuda visited Nan-in three times. Each time Nan-in told him the same thing. &quot;A phsisician should not waste time around here. Go home and take care of your patients.&quot;</p>

                    <p>It was not clear to Kusuda how such teaching could remove the fear of death. So on the forth visit he complained: &quot;My friend told me that when one learns Zen one loses his fear of death. Each time I come here you tell me to take care of my patients. I know that much. If that is your so-called Zen, I am not going to visit you anymore.&quot;</p>

                    <p>Nan-in smiled and patted the doctor. &quot;I have been too strict with you. Let me give you a koan.&quot; He presented Kusuda with Joshu&#39;s Mu to work over, which is the first mind-enlightening problem in the book called <em>The Gateless Gate.</em></p>

                    <p>Kusuda pondered this problem of Mu (No-Thing) for two years. At length he thought he had reached certainty of mind. But his teacher commented: &quot;You are not in yet.&quot;</p>

                    <p>Kusuda continued in concentration for another yet and a half. His mind became placid. Problems dissolved. No-Thing became the truth. He served his patients well and, without even knowing it, he was free from concern of life and death.</p>

                    <p>Then he visited Nan-in, his old teacher just smiled.</p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="A Parable"
            koan="""
                    <p>Buddha told a parable in sutra:</p>

                    <p>A man traveling across a field encountered a tiger. He fled, the tiger after him. Coming to a precipice, he caught hold of the root of a wild vine and swung himself down over the edge. The tiger sniffed at him from above. Trembling, the man looked down to where, far below, another tiger was waiting to eat him. Only the vine sustained him.</p>

                    <p>Two mice, one white and one black, little by little started to gnaw away the vine. The man saw a luscious strawberry near him. Grasping the vine with one hand, he plucked the strawberry with the other. How sweet it tasted!</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="The First Principle"
            koan="""
                    <p>When one goes to Obaku temple in Kyoto he sees carved over the gate the words &quot;The First Principle&quot;. The letters are unusually large, and those who appreciate calligraphy always admire them as being a mastepiece. They were drawn by Kosen two hundred years ago.</p>

                    <p>When the master drew them he did so on paper, from which the workmen made the large carving in wood. As Kosen sketched the letters a bold pupil was with him who had made several gallons of ink for the calligraphy and who never failed to criticise his master&#39;s work.</p>

                    <p>&quot;That is not good,&quot; he told Kosen after his first effort.</p>

                    <p>&quot;How is this one?&quot;</p>

                    <p>&quot;Poor. Worse than before,&quot; pronounced the pupil.</p>

                    <p>Kosen patiently wrote one sheet after another until eighty-four First Principles had accumulated, still without the approval of the pupil.</p>

                    <p>Then when the young man stepped outside for a few moments, Kosen thought: &quot;Now this is my chance to escape his keen eye,&quot; and he wrote hurriedly, with a mind free from distraction: &quot;The First Principle.&quot;</p>

                    <p>&quot;A masterpiece,&quot; pronounced the pupil.</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="A Mother's Advice"
            koan="""
                    <p>Jiun, a Shingon master, was a well-known Sanskrit scholar of the Tokugawa era. When he was young he used to deliver lectures to his brother students.</p>

                    <p>His mother heard about this and wrote him a letter:</p>

                    <p>&quot;Son, I do not think you became a devotee of the Buddha because you desired to turn into a walking dictionary for others. There is no end to information and commentation, glory and honor. I wish you would stop this lecture business. Shut yourself up in a little temple in a remote part of the mountain. Devote your time to meditation and in this way attain true realization.&quot;</p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="The Sound of One Hand"
            koan="""
                    <p>The master of Kennin temple was Mokurai, Silent Thunder. He had a little protege named Toyo who was only twelve years old. Toyo saw the older disciples visit the master&#39;s room each morning and evening to receive instruction in sanzen or personal guidance in which they were given koans to stop mind-wandering.</p>

                    <p>Toyo wished to do sanzen also.</p>

                    <p>&quot;Wait a while,&quot; said Mokurai. &quot;You are too young.&quot;</p>

                    <p>But the child insisted, so the teacher finally consented.</p>

                    <p>In the evening little Toyo went at the proper time to the threshold of Mokurai&#39;s sanzen room. He struck the gong to announce his presence, bowed respectfully three times outside the door, and went to sit before the master in respectful silence.</p>

                    <p>&quot;You can hear the sound of two hands when they clap together,&quot; said Mokurai. &quot;Now show me the sound of one hand.&quot;</p>

                    <p>Toyo bowed and went to his room to consider this problem. From his window he could hear the music of the geishas. &quot;Ah, I have it!&quot; he proclaimed.</p>

                    <p>The next evening, when his teacher asked him to illustrate the sound of one hand, Toyo began to play the music of the geishas.</p>

                    <p>&quot;No, no,&quot; said Mokurai. &quot;That will never do. That is not the sound of one hand. You&#39;ve not got it at all.&quot;</p>

                    <p>Thinking that such music might interrupt, Toyo moved his abode to a quiet place. He meditated again. &quot;What can the sound of one hand be?&quot; He happened to hear some water dripping. &quot;I have it,&quot; imagined Toyo.</p>

                    <p>When he next appeared before his teacher, Toyo imitated dripping water.</p>

                    <p>&quot;What is that?&quot; asked Mokurai. &quot;That is the sound of dripping water, but not the sound of one hand. Try again.&quot;</p>

                    <p>In vain Toyo meditated to hear the sound of one hand. He heard the sighing of the wind. But the sound was rejected.</p>

                    <p>He heard the cry of an owl. This also was refused.</p>

                    <p>The sound of one hand was not the locusts.</p>

                    <p>For more than ten times Toyo visited Mokurai with different sounds. All were wrong. For almost a year he pondered what the sound of one hand might be.</p>

                    <p>At last little Toyo entered true meditation and transcended all sounds. &quot;I could collect no more,&quot; he explained later, &quot;so I reached the soundless sound.&quot;</p>

                    <p>Toyo had realized the sound of one hand.</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="My Heart Burns Like Fire"
            koan="""
                    <p>Soyen Shaku, the first Zen teacher to come to America, said: &quot;My heart burns like fire but my eyes are as cold as dead ashes.&quot; He made the following rules which he practiced every day of his life.</p>

                    <p>In the morning before dressing, light incense and meditate.</p>

                    <p>Retire at a regular hour. Partake of food at regular intervals. Eat with moderation and never to the point of satisfaction.</p>

                    <p>Receive a guest with the same attitude you have when alone. When alone, maintain the same attitude you have in receiving guests.</p>

                    <p>Watch what you say, and whatever you say, practice it.</p>

                    <p>When an opportunity comes do not let it pass by, yet always think twice before acting.</p>

                    <p>Do not regret the past. Look to the future.</p>

                    <p>Have the fearless attitude of a hero and the loving heart of a child.</p>

                    <p>Upon retiring, sleep as if you had entered your last sleep. Upon awakening, leave your bed behind you instantly as if you had cast away a pair of old shoes.</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Eshun's Departure"
            koan="""
                    <p>When Eshun, the Zen nun, was past sixty and about to leave this world, she asked some monks to pile up wood in the yard.</p>

                    <p>Seating herself firmly in the center of the funeral pyre, she had it set fire around the edges.</p>

                    <p>&quot;O nun!&quot; shouted one monk, &quot;is it hot in there?&quot;</p>

                    <p>&quot;Such a matter would concern only a stupid person like yourself,&quot; answered Eshun.</p>

                    <p>The flames arose, and she passed away.</p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Reciting Sutras"
            koan="""
                    <p>A farmer requested a Tendai priest to recite sutras for his wife, who had died. After the recitation was over the farmer asked: &quot;Do you think my wife will gain merit from this?&quot;</p>

                    <p>&quot;Not only your wife, but all sentient beings will benefit from the recitation of sutras,&quot; answered the priest.</p>

                    <p>&quot;If you say all sentient beings will benefit,&quot; said the farmer, &quot;my wife may be very weak and others will take advantage of her, getting the benefit she should have. So please recite sutras just for her.&quot;</p>

                    <p>The priest explained that it was the desire of a Buddhist to offer blessings and wish merit for every living being.</p>

                    <p>&quot;That is a fine teaching,&quot; concluded the farmer, &quot;but please make one exception. I have a neighbor who is rough and mean to me. Just exclude him from all those sentient beings.&quot;</p>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Three Days More"
            koan="""
                    <p>Suiwo, the disciple of Hakuin, was a good teacher. During one summer seclusion period, a pupil came to him from a southern island of Japan.</p>

                    <p>Suiwo gave him the problem: &quot;Hear the sound of one hand.&quot;</p>

                    <p>The pupil remained three years but could not pass this test. One night he came in tears to Suiwo. &quot;I must return south in shame and embarrassment,&quot; he said, &quot;for I cannot solve my problem.&quot;</p>

                    <p>&quot;Wait one week more and meditate constantly,&quot; advised Suiwo. Still no enlightenment came to the pupil. &quot;Try for another week,&quot; said Suiwo. The pupil obeyed, but in vain.</p>

                    <p>&quot;Still another week.&quot; Yet this was of no avail. In despair the student begged to be released, but Suiwo requested another meditation of five days. They were without result. Then he said: &quot;Meditate for three days longer, then if you fail to attain enlightenment, you had better kill yourself.&quot;</p>

                    <p>On the second day the pupil was enlightened.</p>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Trading Dialogue for Lodging"
            koan="""
                    <p>Provided he makes and wins an argument about Buddhism with those who live there, any wondering monk can remain in a Zen temple. If he is defeated, he has to move on.</p>

                    <p>In a temple in the northern part of Japan two brother monks were dwelling together. The elder one was learned, but the younger one was stupid and had but one eye.</p>

                    <p>A wandering monk came and asked for lodging, properly challenging them to a debate about the sublime teachings. The elder brother, tired that day from much studying, told the younger one to take his place. &quot;Go and request the dialogue in silence,&quot; he cautioned.</p>

                    <p>So the young monk and the stranger went to the shrine and sat down.</p>

                    <p>Shortly afterwards the traveler rose and went in to the elder brother and said: &quot;Your young brother is a wonderful fellow. He defeated me.&quot;</p>

                    <p>&quot;Relate the dialogue to me,&quot; said the elder one.</p>

                    <p>&quot;Well,&quot; explained the traveler, &quot;first I held up one finger, representing Buddha, the enlightened one. So he held up two fingers, signifying Buddha and his teaching. I held up three fingers, representing Buddha, his teaching, and his followers, living the harmonious life. Then he shook his clenched fist in my face, indicating that all three come from one realization. Thus he won and so I have no right to remain here.&quot; With this, the traveler left.</p>

                    <p>&quot;Where is that fellow?&quot; asked the younger one, running in to his elder brother.</p>

                    <p>&quot;I understand you won the debate.&quot;</p>

                    <p>&quot;Won nothing. I&#39;m going to beat him up.&quot;</p>

                    <p>&quot;Tell me the subject of the debate,&quot; asked the elder one.</p>

                    <p>&quot;Why, the minute he saw me he held up one finger, insulting me by insinuating that I have only one eye. Since he was a stranger I thought I would be polite to him, so I held up two fingers, congratulating him that he has two eyes. Then the impolite wretch held up three&nbsp;fingers, suggesting that between us we only have three eyes. So I got mad and started to punch him, but he ran out and that ended it!&quot;</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="The Voice of Happiness"
            koan="""
                    <p>After Bankei had passed away, a blind man who lived near the master&#39;s temple told a friend:</p>

                    <p>&quot;Since I am blind, I cannot watch a person&#39;s face, so I must judge his character by the sound of his voice. Ordinarily when I hear someone congratulate another upon his happiness or success, I also hear a secret tone of envy. When condolence is expressed for the misfortune of another, I hear pleasure and satisfaction, as if the one condoling was really glad there was something left to gain in his own world.</p>

                    <p>&quot;In all my experience, however, Bankei&#39;s voice was always sincere. Whenever he expressed happiness, I heard nothing but happiness, and whenever he expressed sorrow, sorrow was all I heard.&quot;</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Open Your Own Treasure House"
            koan="""
                    <p>Daiju visited the master Baso in China. Baso asked: &quot;What do you seek?&quot;</p>

                    <p>&quot;Enlightenment,&quot; replied Daiju.</p>

                    <p>&quot;You have your own treasure house. Why do you search outside?&quot; Baso asked.</p>

                    <p>Daiju inquired: &quot;Where is my treasure house?&quot;</p>

                    <p>Baso answered: &quot;What you are asking is your treasure house.&quot;</p>

                    <p>Daiju was enlightened! Ever after he urged his friends: &quot;Open your own tresure house and use those treasures.&quot;</p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="No Water, No Moon"
            koan="""
                    <p>When the nun Chiyono studied Zen under Bukko of Engaku she was unable to attain the fruits of meditation for a long time.</p>

                    <p>At last one moonlit night she was carrying water in an old pail bound with bamboo. The bamboo broke and the bottom fell out of the pail, and at that moment Chiyono was set free!</p>

                    <p>In commemoration, she wrote a poem:</p>

                    <p><em>In this way and that I tried to save the old pail<br />
                    Since the bamboo strip was weakening and about to break<br />
                    Until at last the bottom fell out.<br />
                    No more water in the pail!<br />
                    No more moon in the water!</em></p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Calling Card"
            koan="""
                    <p>Keichu, the great Zen teacher of the Meiji era, was the head of Tofuku, a cathedral in Kyoto. One day the governor of Kyoto called upon him for the first time.</p>

                    <p>His attendant presented the card of the governor, which read: Kitagaki, Governor of Kyoto.</p>

                    <p>&quot;I have no business with such a fellow,&quot; said Keichu to his attendant. &quot;Tell him to get out of here.&quot;</p>

                    <p>The attendant carried the card back with apologies. &quot;That was my error,&quot; said the governor, and with a pencil he scratched out the words Governor of Kyoto. &quot;Ask your teacher again.&quot;</p>

                    <p>&quot;Oh, is that Kitagaki?&quot; exclaimed the teacher when he saw the card. &quot;I want to see that fellow.&quot;</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Everything Is Best"
            koan="""
                    <p>When Banzan was walking through a market he overheard a conversation between a butcher and his customer.</p>

                    <p>&quot;Give me the best piece of meat you have,&quot; said the customer.</p>

                    <p>&quot;Everything in my shop is the best,&quot; replied the butcher. &quot;You cannot find here any piece of meat that is not the best.&quot;</p>

                    <p>At these words Banzan became enlightened.</p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Inch Time Foot Gem"
            koan="""
                    <p>A lord asked Takuan, a Zen Teacher, to suggest how he might pass the time. He felt his days very long attending his office and sitting stiffly to receive the homage of others.</p>

                    <p>Takuan wrote eight Chinese characters and gave them to the man:</p>

                    <p><em>Not twice this day<br />
                    Inch time foot gem.<br />
                    This day will not come again.<br />
                    Each minute is worth a priceless gem.</em></p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Mokusen's Hand"
            koan="""
                    <p>Mokusen Hiki was living in a temple in the province of Tamba. One of his adherents complained of the stinginess of his wife.</p>

                    <p>Mokusen visited the adherent&#39;s wife and showed her his clenched fist before her face.</p>

                    <p>&quot;What do you mean by that?&quot; asked the surprised woman.</p>

                    <p>&quot;Suppose my fist were always like that. What would you call it?&quot; he asked.</p>

                    <p>&quot;Deformed,&quot; replied the woman.</p>

                    <p>Then he opened his hand flat in her face and asked: &quot;Suppose it were always like that. What then?&quot;</p>

                    <p>&quot;Another kind of deformity,&quot; said the wife.</p>

                    <p>&quot;If you understand that much,&quot; finished Mokusen, &quot;you are a good wife.&quot; Then he left.</p>

                    <p>After his visit, this wife helped her husband to distribute as well as to save.</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="A Smile in His Lifetime"
            koan="""
                    <p>Mokugen was never known to smile until his last day on earth. When his time came to pass away he said to his faithful ones: &quot;You have studied under me for more than ten years. Show me your real interpretation of Zen. Whoever expresses this most clearly shall be my successor and receive my robe and bowl.&quot;</p>

                    <p>Everyone watched Mokugen&#39;s severe face, but no one answered.</p>

                    <p>Encho, a disciple who had been with his teacher for a long time, moved near the bedside. He pushed forward the medicine cup a few inches. That was his answer to the command.</p>

                    <p>The teacher&#39;s face became even more severe. &quot;Is that all you understand?&quot; he asked.</p>

                    <p>Encho reached out and moved the cup back again.</p>

                    <p>A beautiful smile broke over the features of Mokugen. &quot;You rascal,&quot; he told Encho. &quot;You worked with me ten years and have not yet seen my whole body. Take the robe and bowl. They belong to you.&quot;</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Every-Minute Zen"
            koan="""
                    <p>Zen students are with their masters at least ten years before they presume to teach others. Nan-in was visited by Tenno, who, having passed his apprenticeship, had become a teacher. The day happened to be rainy, so Tenno wore wooden clogs and carried an umbrella. After greeting him Nan-in remarked: &quot;I suppose you left your wodden clogs in the vestibule. I want to know if your umbrella is on the right or left side of the clogs.&quot;</p>

                    <p>Tenno, confused, had no instant answer. He realized that he was unable to carry his Zen every minute. He became Nan-in&#39;s pupil, and he studied six more years to accomplish his every-minute Zen.</p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Flower Shower"
            koan="""
                    <p>Subhuti was Buddha&#39;s disciple. He was able to understand the potency of emptiness, the viewpoint that nothing exists except in its relationship of subjectivity and objectivity.</p>

                    <p>One day Subhuti, in a mood of sublime emptiness, was sitting under a tree. Flowers began to fall about him.</p>

                    <p>&quot;We are praising you for your discourse on emptiness,&quot; the gods whispered to him.</p>

                    <p>&quot;But I have not spoken of emptiness,&quot; said Subhuti.</p>

                    <p>&quot;You have not spoken of emptiness, we ahve not heard emptiness,&quot; responded the gods. &quot;This is the true emptiness.&quot; And blossoms showered upon Subhuti as rain.</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Publishing the Sutras"
            koan="""
                    <p>Tetsugen, a devotee of Zen in Japan, decided to publish the sutras, which at that time were available only in Chinese. The books were to be printed with wood blocks in an edition of seven thousand copies, a tremendous undertaking.</p>

                    <p>Tetsugen began by traveling and collecting donations for this purpose. A few sympathizers would give him a hundred pieces of gold, but most of the time he received only small coins. He thanked each donor with equal gratitude. After ten years Tetsugen had enough money to begin his task.</p>

                    <p>It happened that at that time the Uji Rive overflowed. Famine followed. Tetsugen took the funds he had collected for the books and spent them to save others from starvation. Then he began again his work of collecting.</p>

                    <p>Several years afterwards an epidemic spread over the country. Tetsugen again gave away what he had collected, to help his people. For a third time he started his work, and after twenty years his wish was fulfilled. The printing blocks which produced the first edition of sutras can be seen today in the Obaku monastery in Kyoto.</p>

                    <p>The Japanese tell their children that Tetsugen made three sets of sutras, and that the first two invisible sets surpass even the last.</p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Gisho's Work"
            koan="""
                    <p>Gisho was ordained as a nun when she was just ten years old. She received training just as the little boys did. When she reached the age of sixteen she traveled from one Zen master to another, studying with them all.</p>

                    <p>She remained three years with Unzan, six years with Gukei, but was unable to obtain a clear vision. At last she went to the master Inzan.</p>

                    <p>Inzan showed her no distinction at all on account of her sex. He scolded her like a thunderstorm. He cuffed he to awaken her inner nature.</p>

                    <p>Gisho remained with Inzan thirteen years, and then she found that which she was seeking!</p>

                    <p>In her honor, Inzan wrote a poem:</p>

                    <p><em>This nun studied thirteen years under my guidance.<br />
                    <br />
                    In the evening she considered the deepest koans,<br />
                    In the morning she was wrapped in other koans.<br />
                    The Chinese nun Tetsuma surpassed all before her,<br />
                    And since Mujaku none has been so genuine as this Gisho!<br />
                    Yet there are many more gates for her to pass through.<br />
                    She should receive still more blows from my iron fist.</em></p>

                    <p>After Gisho was enlightened she went to the province of Banshu, started her own Zen temple, and taught two hundred other nuns until she passed away one year in the month of August.</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Sleeping in the Daytime"
            koan="""
                    <p>The master Soyen Shaku passed from this world when he was sixty-one years of age. Fulfilling his life&#39;s work, he left a great teaching, far richer than that of most Zen masters. His pupils used to sleep in the daytime during midsummer, and while he overlooked this he himself never wasted a minute.</p>

                    <p>When he was but twelve years old he was already studying Tendai philosophical speculation. One summer day the air had been so sultry that little Soyen stretched his legs and went to sleep while his teacher was away.</p>

                    <p>Three hours passed when, suddenly waking, he heard his master enter, but it was too late. There he lay, sprawled across the doorway.</p>

                    <p>&quot;I beg your pardon, I beg your pardon,&quot; his teacher whispered, stepping carefully over Soyen&#39;s body as if it were that of some distinguished guest. After this, Soyen never slept again in the afternoon.</p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="In Dreamland"
            koan="""
                    <p>&quot;Our schoolmaster used to take a nap every afternoon,&quot; related a disciple of Soyen Shaku. &quot;We children asked him why he did it and he told us: &#39;I go to dreamland to meet the old sages just as Confucius did.&#39; When Confucius slept, he would dream of ancient sages and later tell his followers about them.</p>

                    <p>&quot;It was extremely hot one day so some of us took a nap. Our schoolmaster scolded us. &#39;We went to dreamland to meet the ancient sages the same as Confucius did,&#39; we explained. &#39;What was the message from those sages?&#39; our schoolmaster demanded. One of us replied: &#39;We went to dreamland and met the sages and asked them if our schoolmaster came there every afternoon, but they said they had never seen any such fellow.&#39;&quot;</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Joshu's Zen"
            koan="""
                    <p>Joshu began the study of Zen when he was sixty years old and continued until he was eighty, when he realized Zen.</p>

                    <p>He taught from the age of eighty until he was one hundred and twenty.</p>

                    <p>A student once asked him: &quot;If I haven&#39;t anything in my mind, what shall I do?&quot;</p>

                    <p>Joshu replied: &quot;Throw it out.&quot;</p>

                    <p>&quot;But if I haven&#39;t anything, how can I throw it out?&quot; continued the questioner.</p>

                    <p>&quot;Well,&quot; said Joshu, &quot;then carry it out.&quot;</p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="The Dead Man's Answer"
            koan="""
                    <p>When Mamiya, who later became a well-known preacher, went to a teacher for personal guidance, he was asked to explain the sound of one hand.</p>

                    <p>Mamiya concentrated upon what the sound of one hand might be. &quot;You are not working hard enough,&quot; his teacher told him. &quot;You are too attached to food, wealth, things, and that sound. It would be better if you died. That would solve the problem.&quot;</p>

                    <p>The next time Mamiya appeared before his teacher he was again asked what he had to show regarding the sound of one hand. Mamiya at once fell over as if he were dead.</p>

                    <p>&quot;You are dead all right,&quot; observed the teacher, &quot;But how about that sound?&quot;</p>

                    <p>&quot;I haven&#39;t solved that yet,&quot; replied Mamiya, looking up.</p>

                    <p>&quot;Dead men do not speak,&quot; said the teacher. &quot;Get out!&quot;</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Zen in a Beggar's Life"
            koan="""
                    <p>Tosui was a well-known Zen teacher of his time. He had lived in several temples and taught in various provinces.</p>

                    <p>The last temple he visited accumulated so many adherents that Tosui told them he was going to quit the lecture business entirely. He advised them to disperse and to go wherever they desired. After that no one could find any trace of him.</p>

                    <p>Three years later one of his disciples discovered him living with some beggars under a bridge in Kyoto. He at one implored Tosui to teach him.</p>

                    <p>&quot;If you can do as I do for even a couple of days, I might,&quot; Tosui replied.</p>

                    <p>So the former disciple dressed as a beggar and spent a day with Tosui. The following day one of the beggars died. Tosui and his pupil carried the body off at midnight and buried it on a mountainside. After that they returned to their shelter under the bridge.</p>

                    <p>Tosui slept soundly the remainder of the night, but the disciple could not sleep. When morning came Tosui said: &quot;We do not have to beg food today. Our dead friend has left some over there.&quot; But the disciple was unable to eat a single bite of it.</p>

                    <p>&quot;I have said you could not do as I,&quot; concluded Tosui. &quot;Get out of here and do not bother me again.&quot;</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="The Thief Who Became a Disciple"
            koan="""
                    <p>One evening as Shichiri Kojun was reciting sutras a thief with a sharp sword entered, demanding wither his money or his life.</p>

                    <p>Shichiri told him: &quot;Do not disturb me. You can find the money in that drawer.&quot; Then he resumed his recitation.</p>

                    <p>A little while afterwards he stopped and called: &quot;Don&#39;t take it all. I need some to pay taxes with tomorrow.&quot;</p>

                    <p>The intruder gathered up most of the money and started to leave. &quot;Thank a person when you receive a gift,&quot; Shichiri added. The man thanked him and made off.</p>

                    <p>A few days afterwards the fellow was caught and confessed, among others, the offense against Shichiri. When Shichiri was called as a witness he said: &quot;This man is no thief, at least as far as I am concerned. I gave him the money and he thanked me for it.&quot;</p>

                    <p>After he had finished his prison term, the man went to Shichiri and became his disciple.</p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Right & Wrong"
            koan="""
                    <p>When Bankei held his seclusion-weeks of meditation, pupils from many parts of Japan came to attend. During one of these gatherings a pupil was caught stealing. The matter was reported to Bankei with the request that the culprit be expelled. Bankei ignored the case.</p>

                    <p>Later the pupil was caught in a similar act, and again bankei disregarded the matter. this angered the other pupils, who drew up a petition asking for the dismissal of the thief, stating that otherwise they woudl leave in a body.</p>

                    <p>When bankei had read the petition he called everyone before him. &quot;You are wise brothers,&quot; he told them. &quot;You know what is right and what is not right. You may somewhere else to study if ou wish, but this poor brother does not even know right from wrong. Who will teach him if I do not? I am going to keep him here even if all the rest of you leave.&quot;</p>

                    <p>A torrent of tears cleansed the face of the brother who had stolen. All desire to steal had vanished.</p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="How Grass & Trees Become Enlightened"
            koan="""
                    <p>During the Kamakura period, Shinkan studied Tendai six years and then studied Zen seven years; then he went to China and contemplated Zen for thirteen years more.</p>

                    <p>When he returned to Japan many desired to interview him and asked onscure questions. But when Shinkan received visitors, which was infrequently, he seldom answered their questions.</p>

                    <p>One day a fifty-year-old student of enlightenment said to Shinkan: &quot;I have studied the Tendai school of thought since I was a little boy, but one thing in it I cannot understand. Tendai claims that even the grass and trees will become enlightened. To me this eems very strange.&quot;</p>

                    <p>&quot;Of what use is it to discuss how grass and trees become enlightened?&quot; asked Shinkan. &quot;The question is how you yourself can become so. Did you ever consider that?&quot;</p>

                    <p>&quot;I never thought of it in that way,&quot; marveled the old man.</p>

                    <p>&quot;Then go home and think it over,&quot; finished Shinkan.</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="The Stingy Artist"
            koan="""
                    <p>Gessen was an artist monk. Before he would start a drawing or painting he always insisted upon being paid in advance, and his fees were high. He was known as the &quot;Stingy Artist.&quot;</p>

                    <p>A geisha once gave him a commission for a painting. &quot;How much can you pay?&quot; inquired Gessen.</p>

                    <p>&quot;Whatever you charge,&quot; replied the girl, &quot;but I want you to do the work in front of me.&quot;</p>

                    <p>So on a certain day Gessen was called by the geisha. She was holding a feast for her patron.</p>

                    <p>Gessen with fine brush work did the painting. When it was completed he asked the highest sum of his time.</p>

                    <p>He received his pay. Then the geisha turned to her patron, saying: &quot;All this artist wants is money. His paintings are fine but his mind is dirty; money has caused it to become muddy. Drawn by such a filthy mind, his work is not fit to exhibit. It is just about good enough for one of my petticoats.&quot;</p>

                    <p>Removing her skirt, she then asked Gessen to do another picture on the back of her petticoat.</p>

                    <p>&quot;How much will you pay?&quot; asked Gessen.</p>

                    <p>&quot;Oh, any amount,&quot; answered the girl.</p>

                    <p>Gessen named a fancy price, painted the picture in the manner requested, and went away.</p>

                    <p>It was learned later that Gessen had these reasons for desiring money:</p>

                    <p>A ravaging famine often visited his province. The rich would not help the poor, so Gessen had a secret warehouse, unknown to anyone, which he kept filled with grain, prepared for those emergencies.</p>

                    <p>From his village to the National Shrine the road was in very poor condition and many travellers suffered while traversing it. He desired to build a better road.</p>

                    <p>His teacher had passed away without realizing his wish to build a temple, and Gessen wished to complete this temple for him.</p>

                    <p>After Gessen had accomplished his three wishes he threw away his brushes and artist&#39;s materials and, retiring to the mountains, never painted again.</p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Accurate Proportion"
            koan="""
                    <p>Sen no Rikyu, a tea-master, wished to hang a flower basket on a column. he asked a carpenter to help him, directing the man to place it a little higher or lower, to the right or left, until he had found exactly the right spot. &quot;That&#39;s the place,&quot; said Sen no Rikyu finally.</p>

                    <p>The carpenter, to test the master, marked the spot and then pretended he had forgotten. Was this the place? &quot;Was this the place, perhaps?&quot; the carpenter kept asking, pointing to various places on the column.</p>

                    <p>But so accurate was the tea-master&#39;s sense of proportion that it was not until the carpenter reached the identical spot again that its location was approved.</p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Black-Nosed Buddha"
            koan="""
                    <p>A nun who was searching for enlightenment made a statue of Buddha and covered it with gold leaf. Wherever she went she carried this golden Buddha with her.</p>

                    <p>Years passed and, still carrying her Buddha, the nun came to live in a small temple in a country where there were many Buddhas, each one with its own particular shrine.</p>

                    <p>The nun wished to burn incense before her golden Buddha. Not liking the idea of the perfume straying to the others, she devised a funnel through which the smoke would ascend only to her statue. This blackened the nose of the golden Buddha, making it especially ugly.</p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Ryonen's Clear Realization"
            koan="""
                    <p>The Buddhist nun known as Ryonen was born in 1797. She was a granddaughter of the famous Japanese warrior Shingen. Her poetical genius and alluring beauty were such that at seventeen she was serving the empress as one of the ladies of the court. Even at such a youthful age fame awaited her.</p>

                    <p>The beloved empress died suddenly and Ryonen&#39;s hopeful dreams vanished. She became acutely aware of the impermanency of life in this world. It was then that she desired to study Zen.</p>

                    <p>Her relatives disagreed, however, and practically forced her into marriage. With a promise that she might become a nun after she had borne three children, Ryonen assented. Before she was twenty-five she had accomplished this condition. Then her husband and relatives could no longer dissuade her from her desire. She shaved her head, took the name of Ryonen, which means to realize clearly, and started on her pilgrimage.</p>

                    <p>She came to the city of Edo and asked Tetsugyu to accept her as a disciple. At one glance the master rejected her because she was too beautiful.</p>

                    <p>Ryonen then went to another master, Hakuo. Hakuo refused her for the same reason, saying that her beauty would only make trouble.</p>

                    <p>Ryonen obtained a hot iron and placed it against her face. In a few moments her beauty had vanished forever.</p>

                    <p>Hakuo then accepted her as a disciple.</p>

                    <p>Commemorating this occasion, Ryonen wrote a poem on the back of a little mirror:</p>

                    <p><em>In the service of my Empress I burned incense to<br />
                    perfume my exquisite clothes<br />
                    Now as a homeless mendicant I burn my face to<br />
                    enter a Zen temple.</em></p>

                    <p>When Ryonen was about to pass from this world, she wrote another poem:</p>

                    <p><em>Sixty-six times have these eyes beheld the changing scene of autumn<br />
                    I have said enough about moonlight,<br />
                    Ask no more.<br />
                    Only listen to the voice of pines and cedars when no wind stirs.</em></p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Sour Miso"
            koan="""
                    <p>The cook monk Dairyo, at Bankei&#39;s monastery, decided that he would take good care of his old teacher&#39;s health and give him only fresh miso, a paste of soy beans mixed with wheat and yeast that often ferments. Bankei, noticing that he was being served better miso than his pupils, asked: &quot;Who is the cook today?&quot;</p>

                    <p>Dairyo was sent before him. Bankei learned that according to his age abd position he should eat only fresh miso. So he said to the cook: &quot;Then you think I shouldn&#39;t eat at all.&quot; With this he entered his room and locked the door.</p>

                    <p>Dairyo, sitting outside the door, asked his teacher&#39;s pardon. Bankei would not answer. For seven days Dairyo sat outside and Bankei within.</p>

                    <p>Finally in desperation an adherent called loudly to Bankei: &quot;You may be all right, old teacher, but this young disciple here has to eat. He cannot go without food forever!&quot;</p>

                    <p>At that Bankei opened the door. He was smiling. He told Dairyo: &quot;I insist on eating the same food as the least of my followers. When you become the teacher I do not want you to forget this.&quot;</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Your Light May Go Out"
            koan="""
                    <p>A student of Tendai, a philosophical school of Buddhism, came to the Zen abode of Gasan as a pupil. When he was departing a few years later, Gasan warned him: &quot;Studying the truth speculatively is useful as a way of collecting preaching material. But remember that unless you meditate constantly your light of truth may go out.&quot;</p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="The Giver Should Be Thankful"
            koan="""
                    <p>While Seisetsu was the master of Engaku in Kamakura he required larger quarters, since those in which he was teaching were overcrowded. Umezu Seibei, a merchant of Edo, decided to donate five hundred pieces of gold called ryo toward the construction of a more commodious school. This money he brought to the teacher.</p>

                    <p>Seisetsu said: &quot;All right. I will take it.&quot;</p>

                    <p>Umezu gave Seisetsu the sack of gold, but he was dissatisfied with the attitude of the teacher. One might live a whole year on three ryo, and the merchant had not even been thanked for five hundred.</p>

                    <p>&quot;In that sack are five hundred ryo,&quot; hinted Umezu.</p>

                    <p>&quot;You told me that before,&quot; replied Seisetsu.</p>

                    <p>&quot;Even if I am a wealthy merchant, five hundred ryo is a lot of money,&quot; said Umezu.</p>

                    <p>&quot;Do you want me to thank you for it?&quot; asked Seisetsu.</p>

                    <p>&quot;You ought to,&quot; replied Uzemu.</p>

                    <p>Why should I?&quot; inquired Seisetsu. &quot;The giver should be thankful.&quot;</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="The Last Will & Testament"
            koan="""
                    <p>Ikkyu, a famous Zen teacher of the Ashikaga era, was the son of the emperor. When he was very young, his mother left the palace and went to study Zen in a temple. In this way Prince Ikkyu also became a student. When his mother passed on, she left with him a letter. It read:</p>

                    <p><em>To Ikkyu:<br />
                    <br />
                    I have finished my work in this life and am now returning into Eternity. I wish you to become a good student and to realize your Buddha-nature. You will know if I am in hell and whether I am always with you or not.<br />
                    <br />
                    If you become a man who realizes that the Buddha and his follower Bodhidharma are your own servants, you may leave off studying and work for humanity. The Buddha preached for forty-nine years and in all that time found it not necessary to speak one word. You ought to know why. But if you don&#39;t and yet wish to, avoid thinking fruitlessly.<br />
                    <br />
                    Your Mother,<br />
                    Not born, not dead.<br />
                    September first.</em></p>

                    <p><em>P.S. The teaching of Buddha was mainly for the purpose of enlightening others. If you are dependent on any of its methods, you are naught but an ignorant insect. There are 80,000 books on Buddhism and if you should read all of them and still not see your own nature, you will not understand even this letter. This is my will and testament.</em></p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="The Tea-Master & the Assassin"
            koan="""
                    <p>Taiko, a warrior who lived in Japan before the Tokugawa era, studied Cha-no-yu, tea etiquette, with Sen no Rikyu, a teacher of that aesthetical expression of calmness and contentment.</p>

                    <p>Taiko&#39;s attendant warrior Kato interpreted his superior&#39;s enthusiasm for tea etiquette as negligence of state affairs, so he decided to kill Sen no Rikyu. He pretended to make a social call upon the tea-master and was invited to drink tea.</p>

                    <p>The master, who was well skilled in his art, saw at a glance the warrior&#39;s intention, so he invited Kato to leave his sword outside before entering the room for the ceremony, explaining the Cha-no-yu represents peacefulness itself.</p>

                    <p>Kato would not listen to this. &quot;I am a warrior,&quot; he said. &quot;I always have my sword with me. Cha-no-yu or no Cha-no-yu, I have my sword.&quot;</p>

                    <p>&quot;Very well. Bring your sword in and have some tea,&quot; consented Sen no Rikyu.</p>

                    <p>The kettle was boiling on the charcoal fire. Suddenly Sen no Rikyu tipped it over. Hissing steam arose, filling the room with smoke and ashes. The startled warrior ran outside.</p>

                    <p>The tea-master apologized. &quot;It was my mistake. Come back in and have some tea. I have your sword here covered with ashes and will clean it and give it to you.&quot;</p>

                    <p>In this predicament the warrior realized he could not very well kill the tea-master, so he gave up the idea.</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="The True Path"
            koan="""
                    <p>Just before Ninakawa passed away the Zen master Ikkyu visited him. &quot;Shall I lead you on?&quot; Ikkyu asked.</p>

                    <p>Ninakawa replied: &quot;I came here alone and I go alone. What help could you be to me?&quot;</p>

                    <p>Ikkyu answered: &quot;If you think you really come and go, that is your delusion. Let me show you the path on which there is no coming and no going.&quot;</p>

                    <p>With his words, Ikkyu had revealed the path so clearly that Ninakawa smilled and passed away.</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="The Gates of Paradise"
            koan="""
                    <p>A soldier named Nobushige came to Hakuin, and asked: &quot;Is there really a paradise and a hell?&quot;</p>

                    <p>&quot;Who are you?&quot; inquired Hakuin.</p>

                    <p>&quot;I am a samurai,&quot; the warrior replied.</p>

                    <p>&quot;You, a soldier!&quot; exclaimed Hakuin. &quot;What kind of ruler would have you as his guard? Your face looks like that of a beggar.&quot;</p>

                    <p>Nobushige became so angry that he began to draw his sword, but Hakuin continued: &quot;So you have a sword! Your weapon is probably much too dull to cut off my head.&quot;</p>

                    <p>As Nobushige drew his sword Hakuin remarked: &quot;Here open the gates of hell!&quot;</p>

                    <p>At these words the samurai, perceiving the master&#39;s discipline, sheathed his sword and bowed.</p>

                    <p>&quot;Here open the gates of paradise,&quot; said Hakuin.</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Arresting the Stone Buddha"
            koan="""
                    <p>A merchant bearing fifty rolls of cotton goods on his shoulders stopped to rest from the heat of the day beneath a shelter where a large stone Buddha was standing. There he fell asleep, and when he awoke his goods had disappeared. He immediately reported the matter to the police.</p>

                    <p>A judge named O-oka opened court to investigate. &quot;That stone Buddha must have stolen the goods,&quot; concluded the judge. &quot;He is supposed to care for the welfare of the people, but he has failed to perform his holy duty. Arrest him.&quot;</p>

                    <p>The police arrested the stone Buddha and carried it into the court. A noisy croud followed the statue, curious to learn what kind of a sentence the judge was about to impose.</p>

                    <p>When O-oka appeared on the bench he rebuked the boisterous audience. &quot;What right have you people to appear before the court laughing and joking in this manner? You are in contempt of court and subject to a fine and imprisonment.&quot;</p>

                    <p>The people hastened to apologize. &quot;I shall have to impose a fine on you,&quot; said the judge, &quot;but I will remit it provided each one of you brings one roll of cotton goods to the court within three days. Anyone failing to do this will be arrested.&quot;</p>

                    <p>One of the rolls of cloth which the people brought was quickly recognized by the merchant as his own, and thus the thief was easily discovered. The merchant recovered his goods, and the cotton rolls were returned to the people.</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Soldiers of Humanity"
            koan="""
                    <p>Once a division of the Japanese army was engaged in a sham battle, and some of the officers found it necessary to make their headquarters in Gasan&#39;s temple.</p>

                    <p>Gasan told his cook: &quot;Let the officers have only the same simple fare we eat.&quot;</p>

                    <p>This made the army men angry, as they were used to very deferential treatment. One came to Gasan and said: &quot;Who do you think we are? We are soldiers, sacrificing our lives for our country. Why don&#39;t you treat us accordingly?&quot;</p>

                    <p>Gasan answered sternly: &quot;Who do you think <em>we</em> are? We are soldiers of humanity, aiming to save all sentient beings.&quot;</p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="The Tunnel"
            koan="""
                    <p>Zenkai, the son of a samurai, journeyed to Edo and there became the retainer of a high official. He fell in love with the official&#39;s wife and was discovered. In self-defense, he slew the official. Then he ran away with the wife.</p>

                    <p>Both of them later became thieves. But the woman was so greedy that Zenkai grew disgusted. Finally, leaving her, he journeyed far away to the province of Buzen, where he became a wandering mendicant.</p>

                    <p>To atone for his past, Zenkai resolved to accomplish some good deed in his lifetime. Knowing of a dangerous road over a cliff that had caused the death and injury of many persons, he resolved to cut a tunnel through the mountain there.</p>

                    <p>Begging food in the daytime, Zenkai worked at night digging his tunnel. When thirty years had gone by, the tunnel was 2,280 feet long, 20 feet high, and 30 feet wide.</p>

                    <p>Two years before the work was completed, the son of the official he had slain, who was a skillful swordsman, found Zenkai out and came to kill him in revenge.</p>

                    <p>&quot;I will give you my life willingly,&quot; said Zenkai. &quot;Only let me finish this work. On the day it is completed, then you may kill me.&quot;</p>

                    <p>So the son awaited the day. Several months passed and Zendai kept on digging. The son grew tired of doing nothing and began to help with the digging. After he had helped for more than a year, he came to admire Zenkai&#39;s strong will and character.</p>

                    <p>At last the tunnel was completed and the people could use it and travel in safety.</p>

                    <p>&quot;Now cut off my head,&quot; said Zenkai. &quot;My work is done.&quot;</p>

                    <p>&quot;How can I cut off my own teacher&#39;s head?&quot; asked the younger man with tears in his eyes.</p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Gudo and the Emperor"
            koan="""
                    <p>The emperor Goyozei was studying Zen under Gudo. He inquired: &quot;In Zen this very mind is Buddha. Is this correct?&quot;</p>

                    <p>Gudo answered: &quot;If I say yes, you will think that you understand without understanding. If I say no, I would be contradicting a fact which many understand quite well.&quot;</p>

                    <p>On another day the emperor asked Gudo: &quot;Where does the enlightened man go when he dies?&quot;</p>

                    <p>Gudo answered: &quot;I know not.&quot;</p>

                    <p>&quot;Why don&#39;t you know?&quot; asked the emperor.</p>

                    <p>&quot;Because I have not died yet,&quot; replied Gudo.</p>

                    <p>The emperor hesitated to inquire further about these things his mind ould not grasp. So Gudo beat the floor with his hand as if to awaken him, and the emperor was enlightened!</p>

                    <p>The emperor respected Zen and old Gudo more than ever after his enlightenment, and he even permitted Gudo to wear his hat in the palace in winter. When Gudo was over eighty he used to fall asleep in the midst of his lecture, and the emperor would quietly retire to another room so his beloved teacher might enjoy the rest his aging body required.</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="In the Hands of Destiny"
            koan="""
                    <p>A great Japanese warrior named Nobunaga decided to attack the enemy although he had only one-tenth the number of men the opposition commanded. He knew that he would win, but his soldiers were in doubt.</p>

                    <p>On the way he stopped at a Shinto shrine and told his men: &quot;After I visit the shrine I will toss a coin. If heads comes, we will win; if tails, we will lose. Destiny holds us in her hand.&quot;</p>

                    <p>Nobunaga entered the shrine and offered a silent prayer. He came forth and tossed a coin. Heads appeared. His soldiers were so eager to fight that they won their battle easily.</p>

                    <p>&quot;No one can change the hand of destiny,&quot; his attendant told him after the battle.</p>

                    <p>&quot;Indeed not,&quot; said Nobunaga, showing a coin which had been doubled, with heads facing either way.</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Killing"
            koan="""
                    <p>Gasan instructed his adherents one day: &quot;Those who speak against killing and who desire to spare the lives of all conscious beings are right. It is good to protect even animals and insects. But what about those persons who kill time, what about those who are destroying wealth, and those who destroy political economy? We should not overlook them. Furthermore, what of the one who preaches without enlightenment? He is killing Buddhism.&quot;</p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Kasan Sweat"
            koan="""
                    <p>Kasan was asked to officiate at the funeral of a provincial lord.</p>

                    <p>He had never met lords and nobles before so he was nervous. When the ceremony started, Kasan sweat.</p>

                    <p>Afterwards, when he had returned, he gathered his pupils together. Kasan confessed that he was not yet qualified to be a teacher for he lacked the sameness of bearing in the world of fame that he possessed in the secluded temple. Then Kasan resigned and became the pupil of another master. Eight years later he returned to his former pupils, enlightened.</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="The Subjugation of a Ghost"
            koan="""
                    <p>A young wife fell sick and was about to die. &quot;I love you so much,&quot; she told her husband, &quot;I do not want to leave you. Do not go from me to any other woman. If you do, I will return as a ghost and cause you endless trouble.&quot;</p>

                    <p>Soon the wife passed away. The husband respected her last wish for the first three months, but then he met another woman and fell in love with her. They became engaged to be married.</p>

                    <p>Immediately after the engagement a ghost appeared every night to the man, blaming him for not keeping his promise. The ghost was clever too. She told him exactly what had transpired between himself and his new sweetheart. Whenever he gave his fiancee a present, the ghost would describe it in detail. She would even repeat conversations, and it so annoyed the amn that he could not sleep. Someone advised him to take his problem to a Zen master who lived close to the village. At length, in despair, the poor man went to him for help.</p>

                    <p>&quot;Your former wife became a ghost and knows everything you do, &quot; commented the master. &quot;Whatever you do or say, whatever you give your beloved, she knows. She must be a very wise ghost. Really you should admire such a ghost. The next time she appears, bargain with her. Tell her that she knows so much you can hide nothing from her, and that if she will answer you one question, you promise to break your engagement and remain single.&quot;</p>

                    <p>&quot;What is the question I must ask her?&quot; inquired the man.</p>

                    <p>The master replied: &quot;Take a large handful of soy beans and ask her exactly how many beans you hold in your hand. If she cannot tell you, you will know that she is only a figment of your imagination and will trouble you no longer.&quot;</p>

                    <p>The next night, when the ghost appeared the man flattered her and told her that she knew everything.</p>

                    <p>&quot;Indeed,&quot; replied the ghost, &quot;and I know you went to see that Zen master today.&quot;</p>

                    <p>&quot;And since you know so much,&quot; demanded the man, &quot;tell me how many beans I hold in this hand!&quot;</p>

                    <p>There was no longer any ghost to answer the question.</p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Children of His Majesty"
            koan="""
                    <p>Yamaoka Tesshu was a tutor of the emperor. He was also a master of fencing and a profound student of Zen.</p>

                    <p>His home was the abode of vagabonds. He had but one suit of clothes, for they kept him always poor.</p>

                    <p>The emperor, observing how worn his garments were, gave Yamaoka some money to buy new ones. The next time Yamaoka appeared he wore the same old outfit.</p>

                    <p>&quot;What became of the new clothes, Yamaoka?&quot; asked the emperor.</p>

                    <p>&quot;I provided clothes for the children of Your Majesty,&quot; explained Yamaoka.</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="What Are You Doing! What Are You Saying!"
            koan="""
                    <p>In modern times a great deal of nonsense is talked about masters and disciples, and about the inheritance of a master&#39;s teaching by favorite pupils, entitling them to pass the truth on to their adherents. Of course Zen should be imparted in this way, from heart to heart, and in the past it was really accomplished. Silence and humility reigned rather than profession and assertion. The one who received such a teaching kept the matter hidden even after twenty years. Not until another discovered through his own need that a real master was at hand was it learned hat the teaching had been imparted, and even then the occasion arose quite naturally and the teaching made its way in its own right. Under no circumstances did the teacher even claim &quot;I am the successor of So-and-so.&quot; Such a claim would prove quite the contrary.</p>

                    <p>The Zen master Mu-nan had only one successor. His name was Shoju. After Shoju had completed his study of Zen, Mu-nan called him into his room. &quot;I am getting old,&quot; he said, &quot;and as far as I know, Shoju, you are the only one who will carry on this teaching. Here is a book. It has been passed down from master to master for seven generations. I also have added many points according to my understanding. The book is very valuable, and I am giving it to you to represent your successorship.&quot;</p>

                    <p>&quot;If the book is such an important thing, you had better keep it,&quot; Shoju replied. &quot;I received your Zen without writing and am satisfied with it as it is.&quot;</p>

                    <p>&quot;I know that,&quot; said Mu-nan. &quot;Even so, this work has been carried from master to master for seven generations, so you may keep it as a symbol of having received the teaching. Here.&quot;</p>

                    <p>The two happened to be talking before a brazier. The instant Shoju felt the book in his hands he thrust it into the flaming coals. He had no lust for possessions.</p>

                    <p>Mu-nan, who never had been angry before, yelled: &quot;What are you doing!&quot;</p>

                    <p>Shoju shouted back: &quot;What are you saying!&quot;</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="One Note of Zen"
            koan="""
                    <p>After Kakua visited the emperor he disappeared and no one knew what became of him. He was the first Japanese to study Zen in China, but since he showed nothing of it, save one note, he is not remembered for having brought Zen into his country.</p>

                    <p>Kakua visited China and accepted the true teaching. He did not travel while he was there. Meditating constantly, he lived on a remote part of a mountain. Whenever people found him and asked him to preach he would say a few words and then move to another part of the mountain where he could be found less easily.</p>

                    <p>The emperor heard about Kakua when he returned to Japan and asked him to preach Zen for his edification and that of his subjects.</p>

                    <p>Kakua stood before the emperor in silence. He then produced a flute from the folds of his robe, and blew one short note. Bowing politely, he disappeared.</p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Eating the Blame"
            koan="""
                    <p>Circumstances arose one day which delayed preparation of the dinner of a Soto Zen master, Fugai, and his followers. In haste the cook went to the garden with his curved knife and cut off the tops of green veetables, chopped them together, and made soup, unaware that in his haste he had included a part of a snake in the vegetables.</p>

                    <p>The followers of Fugai thought they had never tasted such great soup. But when the master himself found the snake&#39;s head in his bowl, he summoned the cook. &quot;What is this?&quot; he demanded, holding up the head of the snake.</p>

                    <p>&quot;Oh, thank you, master,&quot; replied the cook, taking the morsel and eating it quickly.</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="The Most Valuable Thing in the World"
            koan="""
                    <p>Sozan, a Chinese Zen master, was asked by a student: &quot;What is the most valuable thing in the world?&quot;</p>

                    <p>The master replied: &quot;The head of a dead cat.&quot;</p>

                    <p>&quot;Why is the head of a dead cat the most valuable thing in the world?&quot; inquired the student.</p>

                    <p>Sozan replied: &quot;Because no one can name its price.&quot;</p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Learning To Be Silent"
            koan="""
                    <p>The pupils of the Tendai school used to study meditation before Zen entered Japan. Four of them who were intimate friends promised one another to observe seven days of silence.</p>

                    <p>On the first day all were silent. Their meditation had begun auspiciously, but when night came and the oil lamps were growing dim one of the pupils could not help exclaiming to a servant: &quot;Fix those lamps.&quot;</p>

                    <p>The second pupil was surprised to hear th first one talk. &quot;We are not supposed to say a word,&quot; he remarked.</p>

                    <p>&quot;You two are stupid. Why did you talk?&quot; asked the third.</p>

                    <p>&quot;I am the only one who has not talked,&quot; concluded the fourth pupil.</p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="The Blockhead Lord"
            koan="""
                    <p>Two Zen teachers, Daigu and Gudo, were invited to visit a lord. Upon arriving, Gudo said to the lord: &quot;You are wise by nature and have an inborn ability to learn Zen.&quot;</p>

                    <p>&quot;Nonsense,&quot; said Daigu. &quot;Why do you flatter thi blockhead? He may be a lord, but he doesn&#39;t know anything of Zen.&quot;</p>

                    <p>So, instead of building a temple for Gudo, the lord built it for Daigu and studied Zen with him.</p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Ten Successors"
            koan="""
                    <p>Zen pupils take a vow that even if they are killed by their teacher, they intend to learn Zen. Usually they cut a finger and seal their resolution with blood. In time the vow has become a mere formality, and for this reason the pupil who died by the hand of Ekido was made to appear a martyr.</p>

                    <p>Ekido had become a severe teacher. His pupils feared him. One of them on duty, striking the gong to tell the time of day, missed his beats when his eye was attracted by a beautiful girl passing the temple gate.</p>

                    <p>At that moment Ekido, who was directly behind him, hit him with a stick and the shock happened to kill him.</p>

                    <p>The pupil&#39;s guardian, hearing of the accident, went directly to Ekido. Knowing that he was not to blame, he praised the master for his severe teaching. Ekido&#39;s attitude was just the same as if the pupil were still alive.</p>

                    <p>After this took place, he was able to produce under his guidance more than ten enlightened successors, a very unusual number.</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="True Reformation"
            koan="""
                    <p>Ryokan devoted his life to the study of Zen. One day he heard that his nephew, despite the admonitions of relatives, was spending his money on a courtesan. Inasmuch as the nephew had taken Ryokan&#39;s place in managing the family estate and the property was in danger of being dissipated, the relatives asked Ryokan to do something about it.</p>

                    <p>Ryokan had to travel a long way to visit his nephew, whom he had not seen for many years. The nephew seemed pleased to meet his uncle again and invited him to remain overnight.</p>

                    <p>All night Ryokan sat in meditation. As he was departing in the morning he said to the young man: &quot;I must be getting old, my hand shakes so. Will you help me tie the string of my straw sandal?&quot;</p>

                    <p>The nephew helped him willingly. &quot;Thank you,&quot; finished Ryokan, &quot;you see, a man becomes older and feebler day by day. Take good care of yourself.&quot; Then Ryokan left, never mentioning a word about the courtesan or the complaints of the relatives. But, from that morning on, the dissipations of the nephew ended.</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Temper"
            koan="""
                    <p>A Zen student came to Bankei and complained: &quot;Master, I have an ungovernable temper. How can I cure it?&quot;</p>

                    <p>&quot;You have something very strange,&quot; replied Bankei. &quot;Let me see what you have.&quot;</p>

                    <p>&quot;Just now I cannot show it to you,&quot; replied the other.</p>

                    <p>&quot;When can you show it to me?&quot; asked Bankei.</p>

                    <p>&quot;It arises unexpectedly,&quot; replied the student.</p>

                    <p>&quot;Then,&quot; concluded Bankei, &quot;it must not be your own true nature. If it were, you could show it to me at any time. When you were born you did not have it, and your parents did not give it to you. Think that over.&quot;</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="The Stone Mind"
            koan="""
                    <p>Hogen, a Chinese Zen teacher, lived alone in a small temple in the country. One day four traveling monks appeared and asked if they might make a fire in his yard to warm themselves.</p>

                    <p>While they were building the fire, Hogen heard them arguing about subjectivity and objectivity. He joined them and said: &quot;There is a big stone. Do you consider it to be inside or outside your mind?&quot;</p>

                    <p>One of the monks replied: &quot;From the Buddhist viewpoint everything is an objectification of mind, so I would say that the stone is inside my mind.&quot;</p>

                    <p>&quot;Your head must feel very heavy,&quot; observed Hogen, &quot;if you are carrying around a stone like that in your mind.&quot;</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="No Attachment to Dust"
            koan="""
                    <p>Zengetsu, a Chinese master of the T&#39;ang dynasty, wrote the following advice for his pupils:</p>

                    <p>Living in the world yet not forming attachments to the dust of the world is the way of a true Zen student.</p>

                    <p>When witnessing the good action of another encourage yourself to follow his example. Hearing of the mistaken action of another, advise yourself not to emulate it.</p>

                    <p>Even though alone in a dark room, be as if you were facing a noble guest. Express your feelings, but become no more expressive than your true nature.</p>

                    <p>Poverty is your teasure. Never exchange it for an easy life.</p>

                    <p>A person may appear a fool and yet not be one. He may only be guarding his wisdom carefully.</p>

                    <p>Virtues are the fruit of self-discipline and do not drop from heaven of themselves as does rain or snow.</p>

                    <p>Modesty is the foundation of all virtues. Let your neighbors discover you before you make yourself known to them.</p>

                    <p>A noble heart never forces itself forward. Its words are as rare gems, seldom displayed and of great value.</p>

                    <p>To a sincere student, every day is a fortunate day. Time passes but he never lags behind. Neither glory nor shame can move him.</p>

                    <p>Censure yourself, never another. Do not discuss right and wrong.</p>

                    <p>Some things, though right, were considered wrong for generations. Since the value of righteousness may be recognized after centuries, there is no need to crave an immediate appreciation.</p>

                    <p>Live with cause and leave results to the great law of the universe. Pass each day in peaceful contemplation.</p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Real Prosperity"
            koan="""
                    <p>A rich man asked Sengai to write something for the continued prosperity of his family so that it might be treasured from generation to generation.</p>

                    <p>Sengai obtained a large sheet of paper and wrote: &quot;Father dies, son dies, grandson dies.&quot;</p>

                    <p>The rich man became angry. &quot;I asked you to write something for the happiness of my family! Why do you make such a joke as this?&quot;</p>

                    <p>&quot;No joke is intended,&quot; explained Sengai. &quot;If before you yourself die you son should die, this would grieve you greatly. If your grandson should pass away before your son, both of you would be broken-hearted. If your family, generation after generation, passes away in the order I have named, it will be the natural course of life. I call this real prosperity.&quot;</p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Incense Burner"
            koan="""
                    <p>A woman of Nagasaki named Kame was one of the few makers of incense burners in Japan. Such a burner is a work of art to be used only in a tearoom or before a family shrine.</p>

                    <p>Kame, whose father before her had been such an artist, was fond of drinking. She also smoked and associated with men most of the time. Whenever she made a little money she gave a feast inviting artists, poets, carpenters, workers, men of many vocations and avocations. In their association she evolved her designs.</p>

                    <p>Kame was exceedingly slow in creating, but when her work was finished it was always a masterpiece. Her burners were treasured in homes whose womenfolk never drank, smoked, or associated freely with men.</p>

                    <p>The mayor of Nagasaki once requested Kame to design an incense burner for him. She delayed doing so until almost half a year had passed. At that time the mayor, who had been promoted to office in a distant city, visited her. He urged Kame to begin work on his burner.</p>

                    <p>At last receiving the inspiration, Kame made the incense burner. After it was completed she placed it upon a table. She looked at it long and carefully. She smoked and drank before it as if it were her own company. All day she observed it.</p>

                    <p>At last, picking up a hammer, Kame smashed it to bits. She saw it was not the perfect creation her mind demanded.</p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="The Real Miracle"
            koan="""
                    <p>When Bankei was preaching at Ryumon temple, a Shinshu priest, who believed in salvation through the repitition of the name of the Buddha of Love, was jealous of his large audience and wanted to debate with him.</p>

                    <p>Bankei was in the midst of a talk when the priest appeared, but the fellow made such a disturbance that bankei stopped his discourse and asked about the noise.</p>

                    <p>&quot;The founder of our sect,&quot; boasted the priest, &quot;had such miraculous powers that he held a brush in his hand on one bank of the river, his attendant held up a paper on the other bank, and the teacher wrote the holy name of Amida through the air. Can you do such a wonderful thing?&quot;</p>

                    <p>Bankei replied lightly: &quot;Perhaps your fox can perform that trick, but that is not the manner of Zen. My miracle is that when I feel hungry I eat, and when I feel thirsty I drink.&quot;</p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Just Go to Sleep"
            koan="""
                    <p>Gasan was sitting at the bedside of Tekisui three days before his teacher&#39;s passing. Tekisui had already chosen him as his successor.</p>

                    <p>A temple recently had burned and Gasan was busy rebuilding the structure. Tekisui asked him: &quot;What are you going to do when you get the temple rebuilt?&quot;</p>

                    <p>&quot;When your sickness is over we want you to speak there,&quot; said Gasan.</p>

                    <p>&quot;Suppose I do not live until then?&quot;</p>

                    <p>&quot;Then we will get someone else,&quot; replied Gasan.</p>

                    <p>&quot;Suppose you cannot find anyone?&quot; continued Tekisui.</p>

                    <p>Gasan answered loudly: &quot;Don&#39;t ask such foolish questions. Just go to sleep.&quot;</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Nothing Exists"
            koan="""
                    <p>Yamaoka Tesshu, as a young student of Zen, visited one master after another. He called upon Dokuon of Shokoku.</p>

                    <p>Desiring to show his attainment, he said: &quot;The mind, Buddha, and sentient beings, after all, do not exist. The true nature of phenomena is emptiness. There is no relaization, no delusion, no sage, no mediocrity. There is no giving and nothing to be received.&quot;</p>

                    <p>Dokuon, who was smoking quietly, said nothing. Suddenly he whacked Yamaoka with his bamboo pipe. This made the youth quite angry.</p>

                    <p>&quot;If nothing exists,&quot; inquired Dokuon, &quot;where did this anger come from?&quot;</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="No Work, No Food"
            koan="""
                    <p>Hyakujo, the Chinese Zen master, used to labor with his pupils even at the age of eighty, trimming the gardens, cleaning the grounds, and pruning the trees.</p>

                    <p>The pupils felt sorry to see the old teacher working so hard, but they knew he would not listen to their advice to stop, so they hid away his tools.</p>

                    <p>That day the master did not eat. The next day he did not eat, nor the next. &quot;He may be angry because we have hidden his tools,&quot; the pupils surmised. &quot;We had better put them back.&quot;</p>

                    <p>The day they did, the teacher worked and ate the same as before. In the evening he instructed them: &quot;No work, no food.&quot;</p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="True Friends"
            koan="""
                    <p>A long time ago in China there were two friends, one who played the harp skillfully and one who listened skillfully.</p>

                    <p>When the one played or sang about a mountain, the other would say: &quot;I can see the mountain before us.&quot;</p>

                    <p>When the other played about water, the listener would exclaim: &quot;Here is the running stream!&quot;</p>

                    <p>But the listener fell sick and died. The first friend cut the strings of his harp and never played again. Since that time the cutting of harp strings has always been a sign of intimate friendship.</p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Time to Die"
            koan="""
                    <p>Ikkyu, the Zen master, was very clever even as a boy. His teacher had a precious teacup, a rare antique. Ikkyu happened to break this cup and was greatly perplexed. Hearing the footsteps of his teacher, he held the pieces of the cup behind him. When the master appeared, Ikkyu asked: &quot;Why do people have to die?&quot;</p>

                    <p>&quot;This is natural,&quot; explained the older man. &quot;Everything has to die and has just so long to live.&quot;</p>

                    <p>Ikkyu, producing the shattered cup, added: &quot;It was time for your cup to die.&quot;</p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="The Living Buddha & the Tubmaker"
            koan="""
                    <p>Zen masters give personal gidance in a secluded room. No one enters while teacher and pupil are together.</p>

                    <p>Mokurai, the Zen master of Kennin temple in Kyoto, used ot enjoy talking with merchants and newspapermen as well as with his pupils. A certain tubmaker was almost illiterate. He would ask foolish questions of Mokurai, have tea, and then go away.</p>

                    <p>One day while the tubmaker was there Mokurai wished to give personal guidance to a disciple, so he asked the tubmaker to wait in another room.</p>

                    <p>&quot;I understand you are a living Buddha,&quot; the man protested. &quot;Even the stone Buddhas in the temple never refuse the numerous persons who come together before them. Why then should I be excluded?&quot;</p>

                    <p>Mokurai had to go outside to see his disciple.</p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Three Kinds of Disciples"
            koan="""
                    <p>A Zen master named Gettan lived in the latter part of the Tokugawa era. He used to say: &quot;There are three kinds of disciples: those who impart Zen to others, those who maintain the temples and shrines, and then there are the rice bags and the clothes-hangers.&quot;</p>

                    <p>Gasan expressed the same idea. When he was studying under Tekisui, his teacher was very severe. Sometimes he even beat him. Other pupils would not stand this kind of teaching and quit. Gasan remained, saying: &quot;A poor disciple utilizes a teacher&#39;s influence. A fair disciple admires a teacher&#39;s kindness. A good disciple grows strong under a teacher&#39;s discipline.&quot;</p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="How to Write a Chinese Poem"
            koan="""
                    <p>A well-known Japanese poet was asked how to compose a Chinese poem.</p>

                    <p>&quot;The usual Chinese poem is four lines,&quot; he explained. &quot;The first line contains the initial phrase; the second line, the continuation of that phrase; the third line turns from this subject and begins a new one; and the fourth line brings the first three lines together. A popular Japanese song illustrates this:</p>

                    <p><em>Two daughters of a silk merchant live in Kyoto.<br />
                    The elder is twenty, the younger, eighteen.<br />
                    A soldier may kill with his sword,<br />
                    But these girls slay men with their eyes.&quot;</em></p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Zen Dialogue"
            koan="""
                    <p>Zen teachers train their young pupils to express themselves. Two Zen temples each had a child prot&eacute;g&eacute;. One child, going to obtain vegetables each morning, would meet the other on the way.</p>

                    <p>&quot;Where are you going?&quot; asked the one.</p>

                    <p>&quot;I am going wherever my feet go,&quot; the other responded.</p>

                    <p>This reply puzzled the first child who went to his teacher for help. &quot;Tomorrow morning,&quot; the teacher told him, &quot;when you meet that little fellow, ask him the same question. He will give you the same answer, and then you ask him: &#39;Suppose you have no feet, then where are you going?&#39; That will fix him.&quot;</p>

                    <p>The children met again the following morning.</p>

                    <p>&quot;Where are you going?&quot; asked the first child.</p>

                    <p>&quot;I am going wherever the wind blows,&quot; answered the other.</p>

                    <p>This again nonplussed the youngster, who took his defeat to the teacher.</p>

                    <p>Ask him where he is going if there is no wind,&quot; suggested the teacher.</p>

                    <p>The next day the children met a third time.</p>

                    <p>&quot;Where are you going?&quot; asked the first child.</p>

                    <p>&quot;I am going to the market to buy vegetables,&quot; the other replied.</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="The Last Rap"
            koan="""
                    <p>Tangen had studied with Sengai since childhood. When he was twenty he wanted to leave his teacher and visit others for comparitive study, but Sengai would not permit this. Every time Tangen suggested it, Sengai would give him a rap on the head.</p>

                    <p>Finally Tangen asked an elder brother to coax permission from Sengai. This the brother did and then reported to Tangen: &quot;It is arranged. I have fixed it for you to start on your pilgrimage at once.&quot;</p>

                    <p>Tangen went to Sengai to thank him for his permission. The master answered by giving him another rap.</p>

                    <p>When Tangen related this to his elder brother the other said: &quot;What is the matter? Sengai has no business giving premission and then changing his mind. I will tell him so.&quot; And off he went to see the teacher.</p>

                    <p>&quot;I did not cancel my permission,&quot; said Sengai. &quot;I just wished to give him one last smack over the head, for when he returns he will be enlightened and I will not be able to reprimand him again.&quot;</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="The Taste of Banzo's Sword"
            koan="""
                    <p>Matajuro Yagyu was the son of a famous swordsman. His father, believing that his son&#39;s work was too mediocre to anticipate mastership, disowned him.</p>

                    <p>So Matajuro went to Mount Futara and there found the famous swordsman Banzo. But Banzo confirmed the father&#39;s judgment. &quot;You wish to learn swordsmanship under my guidance?&quot; asked Banzo. &quot;You cannot fulfill the requirements.&quot;</p>

                    <p>&quot;But if I work hard, how many years will it take me to become a master?&quot; persisted the youth.</p>

                    <p>&quot;The rest of your life,&quot; replied Banzo.</p>

                    <p>&quot;I cannot wait that long,&quot; explained Matajuro. &quot;I am willing to pass through any hardship if only you will teach me. If I become your devoted servant, how long might it be?&quot;</p>

                    <p>&quot;Oh, maybe ten years,&quot; Banzo relented.</p>

                    <p>&quot;My father is getting old, and soon I must take care of him,&quot; continued Matajuro. &quot;If I work far more intensively, how long would it take me?&quot;</p>

                    <p>&quot;Oh, maybe thirty years,&quot; said Banzo.</p>

                    <p>&quot;Why is that?&quot; asked Matajuro. &quot;First you say ten and now thirty years. I will undergo any hardship to master this art in the shortest time!&quot;</p>

                    <p>&quot;Well,&quot; said Banzo, &quot;in that case you will have to remain with me for seventy years. A man in such a hurry as you are to get results seldom learns quickly.&quot;</p>

                    <p>&quot;Very well,&quot; declared the youth, understanding at last that he was being rebuked for impatience, &quot;I agree.&quot;</p>

                    <p>Matajuro was told never to speak of fencing and never to touch a sword. He cooked for his master, washed the dishes, made his bed, cleaned the yard, cared for the garden, all without a word of swordsmanship.</p>

                    <p>Three years passed. Still Matajuro labored on. Thinking of his future, he was sad. He had not even begun to learn the art to which he had devoted his life.</p>

                    <p>But one day Banzo crept up behind him and gave him a terrific blow with a wooden sword.</p>

                    <p>The following day, when Matajuro was cooking rice, Banzo again sprang upon him unexpectedly.</p>

                    <p>After that, day and night, Matajuro had to defend himself from unexpected thrusts. Not a moment passed in any day that he did not have to think of the taste of Banzo&#39;s sword.</p>

                    <p>He learned so rapidly he brought smiles to the face of his master. Matajuro became the greatest swordsman in the land.</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Fire-Poker Zen"
            koan="""
                    <p>Hakuin used to tell his pupils about an old woman who had a teashop, praising her understanding of Zen. The pupils refused to believe what he told them and would go to the teashop to find out for themselves.</p>

                    <p>Whenever the woman saw them coming she could tell at once whether they had come for tea or to look into her grasp of Zen. In the former case, she would server them graciously. In the latter, she would beckon to the pupils to come behind her screen. The instant they obeyed, she would strike them with a fire-poker.</p>

                    <p>Nine out of ten of them could not escape her beating.</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Storyteller's Zen"
            koan="""
                    <p>Encho was a famous storyteller. His tales of love stirred the hearts of his listeners. When he narrated a story of war, it was as if the listeners themselves were on the field of battle.</p>

                    <p>One day Encho met Yamaoka Tesshu, a layman who had almost embraced masterhood in Zen. &quot;I understand,&quot; said Yamaoka, &quot;you are the best storyteller in our land and that you make people cry or laugh at will. Tell me my favorite story of the Peach Boy. When I was a little tot I used to sleep beside my mother, and she often related this legend. In the middle of the story I would fall asleep. Tell it to me just as my mother did.&quot;</p>

                    <p>Encho dared not attempt to do this. He requested time to study. Several months later he went to Yamaoka and said: &quot;Please give me the opportunity to tell you the story.&quot;</p>

                    <p>&quot;Some other day,&quot; answered Yamaoka.</p>

                    <p>Encho was keenly disappointed. He studied further and tried again. Yamaoka rejected him many times. When Encho would start to talk Yamaoka would stop him, saying: &quot;You are not yet like my mother.&quot;</p>

                    <p>It took Encho five years to be able to tell Yamaoka the legend as his mother had told it to him.</p>

                    <p>In this way, Yamaoka imparted Zen to Encho.</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Midnight Excursion"
            koan="""
                    <p>Many Zen pupils were studing meditation under the Zen master Sengai. One of them used to arise at night, climb over the temple wall, and go to town on a pleasure jaunt.</p>

                    <p>Sengai, inspecting the dormitory quarters, found this pupil missing one night and also discovered the high stool he had used to scale the well. Sengai removed the stool and stood there in its place.</p>

                    <p>When the wanderer returned, not knowing that Sengai was the stool, he put his feet on the master&#39;s head and jumped down into the grounds. Discovering what he had done, he was aghast.</p>

                    <p>Sengai said: &quot;It is very chilly in the early morning. Do be careful not to catch cold yourself.&quot;</p>

                    <p>The pupil never went out at night again.</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="A Letter to a Dying Man"
            koan="""
                    <p>Bassui wrote the following letter to one of his disciples who was about to die:</p>

                    <p>&quot;The essence of your mind is not born, so it will never die. It is not an existence, which is perishable. It is not an emptiness, which is a mere void. It has neither color nor form. It enjoys no pleasures and suffers no pains.</p>

                    <p>&quot;I know you are very ill. Liek a good Zen student, you are facing that sickness squarely. You may not know exactly who is suffering, but question yourself: What is the essence of this mind? Think only of this. You will need no more. Covet nothing. Your end which is endless is as a snowflake dissolving in the pure air.&quot;</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="A Drop of Water"
            koan="""
                    <p>A Zen master named Gisan asked a young student to bring him a pail of water to cool his bath.</p>

                    <p>The student brought the water and, after cooling the bath, threw on to the ground the little that was left over.</p>

                    <p>&quot;You dunce!&quot; the master scolded him. &quot;Why didn&#39;t you give the rest of the water to the plants? What right have you to waste even a drop of water in this temple?&quot;</p>

                    <p>The young student attained Zen in that instant. He changed his name to Tekisui, which means a drop of water.</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Teaching the Ultimate"
            koan="""
                    <p>In early times in Japan, bamboo-and-paper lanterns were used with candles inside. A blind man, visiting a friend one night, was offered a lantern to carry home with him.</p>

                    <p>&quot;I do not need a lantern,&quot; he said. &quot;Darkness or light is all the same to me.&quot;</p>

                    <p>&quot;I know you do not need a lantern to find your way,&quot; his friend replied, &quot;but if you don&#39;t have one, someone else may run into you. So you must take it.&quot;</p>

                    <p>The blind man started off with the lantern and before he had walked very far someone ran squarely into him.</p>

                    <p>&quot;Look out where you are going!&quot; he exclaimed to the stranger. &quot;Can&#39;t you see this lantern?&quot;</p>

                    <p>&quot;Your candle has burned out, brother,&quot; replied the stranger.</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Non-Attachment"
            koan="""
                    <p>Kitano Gempo, abbot of Eihei temple, was ninety-two years old when he passed away in the year 1933. He endeavored his whole like not to be attached to anything. As a wandering mendicant when he was twenty he happened to meet a traveler who smoked tobacco. As they walked together down a mountain road, they stopped under a tree to rest. The traveler offered Kitano a smoke, which he accepted, as he was very hungry at the time.</p>

                    <p>&quot;How pleasant this smoking is,&quot; he commented. The other gave him an extra pipe and tobacco and they parted.</p>

                    <p>Kitano felt: &quot;Such pleasant things may disturb meditation. Before this goes too far, I will stop now.&quot; So he threw the smoking outfit away.</p>

                    <p>When he was twenty-three years old he studied <em>I-King</em>, the profoundest doctrine of the universe. It was winter at the time and he needed some heavy clothes. He wrote his teacher, who lived a hundred miles away, telling him of his need, and gave the letter to a traveler to deliver. Almost the whole winter passed and neither answer nor clothes arrived. So Kitano resorted to the prescience of <em>I-King</em>, which also teaches the art of divination, to determine whether or not his letter had miscarried. He found that this had been the case. A letter afterwards from his teacher made no mention of clothes.</p>

                    <p>&quot;If I perform such accurate determinative work with <em>I-King</em>, I may neglect my meditation,&quot; felt Kitano. So he gave up this marvelous teaching and never resorted to its powers again.</p>

                    <p>When he was twenty-eight he studied Chinese calligraphy and poetry. He grew so skillful in these arts that his teacher praised him. Kitano mused: &quot;If I don&#39;t stop now, I&#39;ll be a poet, not a Zen teacher.&quot; So he never wrote another poem.</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Tosui's Vinegar"
            koan="""
                    <p>Tosui was the Zen master who left the formalism of temples to live under a bridge with beggars. When he was getting very old, a friend helped him earn his living without begging. He showed Tosui how to collect rice and manufacture vinegar from it, and Tosui did this until he passed away.</p>

                    <p>While Tosui was making vinegar, one of the beggars gave him a picture of the Buddha. Tosui hung it on the wall of his hut and put a sign beside it. The sign read:</p>

                    <p>&quot;Mr. Amida Buddha: This little room is quite narrow. I can let you remain as a transient. But don&#39;t think I am asking you to help me to be reborn in your paradise.&quot;</p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="The Silent Temple"
            koan="""
                    <p>Shoichi was a one-eyed teacher of Zen, sparkling with enlightenment. He taught his disciples in Tofuku temple.</p>

                    <p>Day and night the whole temple stood in silence. There was no sound at all.</p>

                    <p>Even the reciting of sutras was abolished by the teacher. His pupils had nothing to do but meditate.</p>

                    <p>When the master passed away, an old neighbor heard the ringing of bells and the recitation of sutras. Then she knew Shoichi had gone.</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Buddha's Zen"
            koan="""
                    <p>Buddha said: &quot;I consider the positions of kings and rulers as that of dust motes. I observe treasures of gold and gems as so many bricks and pebbles. I look upon the finest silken robes as tattered rags. I see myriad worlds of the universe as small seeds of fruit, and the greatest lake in India as a drop of oil on my foot. I perceive the teachings of the world to be the illusion of magicians. I discern the highest conception of emancipation as a golden brocade in a dream, and view the holy path of the illuminated ones as flowers appearing in one&#39;s eyes. I see meditation as a pillar of a mountain, Nirvana as a nightmare of daytime. I look upon the judgment of right and wrong as the serpentine dance of a dragon, and the rise and fall of beliefs as but traces left by the four seasons.&quot;</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Every Day Is a Good Day"
            koan="""
                    <p>Unmon said: &quot;I do not ask you about fifteen days ago. But what about fifteen days hence? Come, say a word about this!&quot; Since none of the monks answered, he answered for them: &quot;Every day is a good day.&quot;</p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="No Cold and Heat"
            koan="""
                    <p>A monk asked Tozan, &quot;How can we escape the cold and heat?&quot; Tozan replied, &quot;Why not go where there is no cold and heat?&quot; &quot;Is there such a place?&quot; the monk asked. Tozan commented, &quot;When cold, be thoroughly cold; when hot, be hot through and through.</p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="The Short Staff"
            koan="""
                    <p>Shuzan held out his short staff and said, &quot;If you call this a short staff, you oppose its reality. If you do not call it a short staff, you ignore the fact. Now what do you wish to call this?&quot;</p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Joshu's Mu"
            koan="""
                    <p>Joshu (A.D. 778-897) was a famous Chinese Zen Master who lived in Joshu, the province from which he took his name. One day a troubled monk approached him, intending to ask the Master for guidance. A dog walked by. The monk asked Joshu, &quot;Has that dog a Buddha-nature or not?&quot; The monk had barely completed his question when Joshu shouted: &quot;MU!&quot;</p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Seijo's Two Souls"
            koan="""
                    <p>Chokan had a very beautiful daughter named Seijo. He also had a handsome young cousin named Ochu. Joking, he would often comment that they would make a fine married couple. Actually, he planned to give his daughter in marriage to another man. But young Seijo and Ochu took him seriously; they fell in love and thought themselves engaged. One day Chokan announced Seijo&#39;s betrothal to the other man. In rage and despair, Ochu left by boat. After several days journey, much to his astonishment and joy he discovered that Seijo was on the boat with him!</p>

                    <p>They went to a nearby city where they lived for several years and had two children. But Seijo could not forget her father; so Ochu decided to go back with her and ask the father&#39;s forgiveness and blessing. When they arrived, he left Seijo on the boat and went to the father&#39;s house. he humbly apologized to the father for taking his daughter away and asked forgiveness for them both.</p>

                    <p>&quot;What is the meaning of all this madness?&quot; the father exclaimed. Then he related that after Ochu had left, many years ago, his daughter Seijo had fallen ill and had lain comatose in bed since. Ochu assured him that he was mistaken, and, in proof, he brought Seijo from the boat. When she entered, the Seijo lying ill in bed rose to meet her, and the two became one.</p>

                    <p>Zen Master Goso, referring to the legend, observed, &quot;Seijo had two souls, one always sick at home and the other in the city, a married woman with two children. Which was the true soul?&quot;</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Bells and Robes"
            koan="""
                    <p>Zen Master Unmon said: &quot;The world is vast and wide. Why do you put on your robes at the sound of a bell?&quot;</p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Ganto's Two Meals"
            koan="""
                    <p>Kisan paid a visit to Ganto, who was living in quiet seclusion, and asked, &quot;Brother, are you getting two meals regularly?&quot; &quot;The fourth son of the Cho family supports me, and I am very much obliged to him,&quot; said Ganto. &quot;If you do not do your part well, you will be born as an ox in the next life and will have to repay him for what you owed him in this life,&quot; Kisan cautioned.</p>

                    <p>Ganto put his fists on his forehead but said nothing. &quot;If you mean horns,&quot; Kisan said, &quot;you must stick out your fingers on top of your head.&quot; But before he finished speaking, Ganto shouted, &quot;Hey!&quot; Kisan did not understand his meaning and said, &quot;If you know something deeper, why don&#39;t you explain it to me?&quot; Ganto hissed at him and said, &quot;You have been studying Buddhism for thirty years, as I have, and you are still wandering around. I have nothing to do with you. Just get out.&quot; And with these words he shut the door in Kisan&#39;s face.</p>

                    <p>The fourth son of the Cho family happened to be passing by and, out of pity, took Kisan to his home. &quot;Thirty years ago we were close friends,&quot; Kisan said sorrowfully, &quot;but now he has attained something higher than I have and will not impart it to me.&quot;</p>

                    <p>That night Kisan could not sleep. He got up and went to Ganto&#39;s house. &quot;Brother,&quot; he implored, &quot;please be kind and preach the Dharma for me.&quot; Ganto opened the door and disclosed the teaching. The next morning Kisan returned home, happy with attainment.</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Bodhidharma and the Emperor Wu"
            koan="""
                    <p>Emperor Wu of China was a very benevolent Buddhist. He built many temples and monasteries, educated many monks, and performed countless philanthropic deeds in the name of Buddhism. He asked the great teacher Bodhidharma, &quot;What merit is there in my good works?&quot; Bodhidharma replied, &quot;None whatsoever.&quot; The Emperor then asked, &quot;What is the Primal meaning of Holy Reality?&quot; Bodhidharma answered, &quot;Emptiness, not holiness.&quot; The Emperor then queried, &quot;Who, then, is this confronting me?&quot; &quot;I do not know,&quot; was Bodhidharma&#39;s reply. Since the Emperor did not understand, Bodhidharma left his kingdom.</p>

                    <p>Later, the Emperor related this conversation to an adviser, Prince Shiko. Shiko reprimanded him, saying that Bodhidharma was a great teacher possessed of the highest truth. The Emperor, filled with regret, dispatched a messenger to entreat Bodhidharma to return. But Shiko warned, &quot;Even if all the people in the land went, that one will never return.&quot;</p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Returning to the Ordinary World"
            koan="""
                    <p>A monk asked Kegon, &quot;How does an enlightened one return to the ordinary world?&quot; Kegon replied, &quot;A broken mirror never reflects again; fallen flowers never go back to the old branches.&quot;</p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="No Beard"
            koan="""
                    <p>Wakuan complained when he saw a picture of bearded Bodhidharma, &quot;Why hasn&#39;t that fellow a beard?&quot;</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Everything is Best"
            koan="""
                    <p>One day Banzan was walking through a market. He overheard a customer say to the butcher, &quot;Give me the best piece of meat you have.&quot; &quot;Everything in my shop is the best,&quot; replied the butcher. &quot;You can not find any piece of meat that is not the best.&quot; At these words, Banzan was enlightened.</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Manjusri Enters the Gate"
            koan="""
                    <p>One day as Manjusri stood outside the gate, the Buddha called to him, &quot;Manjusri, Manjusri, why do you not enter?&quot; Manjusri replied, &quot;I do not see myself as outside. Why enter?&quot;</p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Where to Meet after Death"
            koan="""
                    <p>Dogo paid a visit to his sick fellow monk, Ungan. &quot;Where can I see you again if you die and leave only your corpse?&quot; Dogo asked. &quot;I will meet you where nothing dies,&quot; Ungan replied. Dogo criticized his response saying, &quot;What you should have said is that there is no place where nothing is born and nothing dies and that we need not see each other at all.&quot;</p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="A Philosopher Asks Buddha"
            koan="""
                    <p>A philosopher asked Buddha: &quot;Without words, without silence, will you tell me the truth?&quot; The Buddha sat quietly. The philosopher then bowed and thanked the Buddha, saying, &quot;With your loving kindness I have cleared away my delusions and entered the true path.&quot; After the philosopher had gone, Ananda asked Buddha what the philosopher had attained. The Buddha commented, &quot;A good horse runs even at the shadow of the whip.&quot;</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Jizo's Buddhism"
            koan="""
                    <p>One day, Jizo received one of Hofuku&#39;s disciples and asked him, &quot;How does your teacher instruct you?&quot; &quot;My teacher instructs me to shut my eyes and see no evil thing; to cover my ears and hear no evil sound; to stop my mind-activities and form no wrong ideas,&quot; the monk replied. &quot;I do not ask you to shut your eyes,&quot; Jizo said, &quot;but you do not see a thing. I do not ask you to cover your ears, but you do not hear a sound. I do not ask you to cease your mind-activities, but you do not form any idea at all.&quot;</p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="The Southern Mountain"
            koan="""
                    <p>Sekiso lived and taught on the Southern Mountain, and Kankei lived and taught on the Northern Mountain. One day, a monk came from the Northern Monastery to the Southern Monastery in search of teaching. Sekiso said to him, &quot;My Southern Monastery is no better than the Monastery in the North.&quot; The monk did not know what reply to make. When he returned to Kankei and told him the story, Kankei said, &quot;You should have told him that I am ready to enter Nirvana any day.&quot;</p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="The Girl Comes Out of Meditation"
            koan="""
                    <p>Once upon a time, Manjusri, the Bodhisattva of Wisdom, went to an assemblage of Buddhas. By the time he arrived, all had departed except for the Buddha Sakyamuni and one girl. She was seated in a place of highest honor, deep in meditation. Manjusri asked the Buddha how it was possible for a mere girl to attain a depth of mediation that even he could not attain. The Buddha said, &quot;Bring her out of meditation and ask her yourself.&quot;</p>

                    <p>So Manjusri walked around the girl three times [a gesture of reverence], then snapped his fingers. She remained deep in meditation. He then tried rousing her by invoking all his magic powers; he even transported her to a high heaven. All was to no avail, so deep was her concentration. But suddenly, up from below the earth sprang Momyo, an unenlightened one. He snapped his fingers once, and the girl came out of her meditation.</p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="The Real Way Is Not Difficult"
            koan="""
                    <p>Joshu addressed an assembly of monks: &quot;The Real Way is not difficult; but it dislikes the Relative. If there is but little speech, it is about the Relative or it is about the Absolute. This old monk is not within the Absolute. Do you value this or not?&quot; A monk said to him, &quot;If you are not within the Absolute, how can you judge its value?&quot; Joshu said, &quot;Neither do I know that.&quot; The monk argued, &quot;Your Reverence, if you do not yet know, how is it that you say you are not within the Absolute?&quot; Joshu said, &quot;Your questioning is effective. Finish your worship and leave.&quot;</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="The Turtle in the Garden"
            koan="""
                    <p>A monk saw a turtle in the garden of Daizui&#39;s monastery and asked the teacher, &quot;All beings cover their bones with flesh and skin. Why does this being cover its flesh and skin with bones?&quot; Master Daizui took off one of his sandals and covered the turtle with it.</p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="The Temple"
            koan="""
                    <p>One day Hofuku said to his disciples, &quot;When one passes behind the temple, he meets Chang and Li, but he does not see anyone in front of it. Why is this? Which of the two roads is better?&quot; A monk answered, &quot;Something must be wrong with the sight. Nothing is gained without seeing.&quot; The Master scolded the monk, saying, &quot;Stupid, the temple is always like this.&quot; The monk said, &quot;If it were not the temple, one should see something.&quot; The Master said, &quot;I am talking about the temple and nothing else.&quot;</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Lotus Blossoms and Leaves"
            koan="""
                    <p>A monk asked Chimon, &quot;Before the lotus blossom has emerged from the water, what is it?&quot; Chimon said, &quot;A lotus blossom.&quot; The monk pursued, &quot;After it has come out of the water, what is it?&quot; Chimon replied, &quot;Lotus leaves.&quot;</p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="A Philosopher Asks Buddha"
            koan="""
                    <p>A philosopher asked Buddha: &#39;Without words, without the wordless, will you you tell me truth?&#39;<br />
                    The Buddha kept silence.<br />
                    The philosopher bowed and thanked the Buddha, saying: &#39;With your loving kindness I have cleared away my delusions and entered the true path.&#39;<br />
                    After the philosopher had gone, Ananda asked the Buddha what he had attained.<br />
                    The Buddha replied, &#39;A good horse runs even at the shadow of the whip.&#39;</p>

                    <p><em>Mumon&#39;s Comment:</em> Ananda was the disciple of the Buddha. Even so, his opinion did not surpass that of outsiders. I want to ask you monks: How much difference is there between disciples and outsiders?</p>

                    <p><em>To tread the sharp edge of a sword<br />
                    To run on smooth-frozen ice,<br />
                    One needs no footsteps to follow.<br />
                    Walk over the cliffs with hands free.</em></p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Joshu's Dog"
            koan="""
                    <p>A monk asked Joshu, a Chinese Zen master: &#39;Has a dog Buddha-nature or not?&#39;<br />
                    Joshu answered: &#39;Mu.&#39; [Mu is the negative symbol in Chinese, meaning &#39;Nothing&#39; or &#39;Nay&#39;.]</p>

                    <p><em>Mumon&#39;s comment:</em> To realize Zen one has to pass through the barrier of the patriarchs. Enlightenment always comes after the road of thinking is blocked. If you do not pass the barrier of the patriarchs or if your thinking road is not blocked, whatever you think, whatever you do, is like a tangling ghost. You may ask: What is a barrier of a patriarch? This one word, Mu, is it.</p>

                    <p>This is the barrier of Zen. If you pass through it you will see Joshu face to face. Then you can work hand in hand with the whole line of patriarchs. Is this not a pleasant thing to do?</p>

                    <p>If you want to pass this barrier, you must work through every bone in your body, through ever pore in your skin, filled with this question: What is Mu? and carry it day and night. Do not believe it is the common negative symbol meaning nothing. It is not nothingness, the opposite of existence. If you really want to pass this barrier, you should feel like drinking a hot iron ball that you can neither swallor nor spit out.</p>

                    <p>Then your previous lesser knowledge disappears. As a fruit ripening in season, your subjectivity and objectivity naturally become one. It is like a dumb man who has had a dream. He knows about it but cannot tell it.</p>

                    <p>When he enters this condition his ego-shell is crushed and he can shake the heaven and move the earth. He is like a great warrior with a sharp sword. If a Buddha stands in his way, he will cut him down; if a patriarch offers him any obstacle, he will kill him; and he will be free in this way of birth and death. He can enter any world as if it were his own playground. I will tell you how to do this with this koan:</p>

                    <p>Just concentrate your whole energy into this Mu, and do not allow any discontinuation. When you enter this Mu and there is no discontinuation, your attainment will be as a candle burning and illuminating the whole universe.</p>

                    <p><em>Has a dog Buddha-nature?<br />
                    This is the most serious question of all.<br />
                    If you say yes or no,<br />
                    You lose your own Buddha-nature.</em></p>

                    <p>&nbsp;</p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Hyakujo's Fox"
            koan="""
                    <p>Once when Hyakujo delivered some Zen lectures an old man attended them, unseen by the monks. At the end of each talk when the monks left so did he. But one day he remained after the had gone, and Hyakujo asked him: &#39;Who are you?&#39;</p>

                    <p>The old man replied: &#39;I am not a human being, but I was a human being when the Kashapa Buddha preached in this world. I was a Zen master and lived on this mountain. At that time one of my students asked me whether the enlightened man is subject to the law of causation. I answered him: &quot;The enlightened man is not subject to the law of causation.&quot; For this answer evidencing a clinging to absoluteness I became a fox for five hundred rebirths, and I am still a fox. Will you save me from this condition with your Zen words and let me get out of a fox&#39;s body? Now may I ask you: Is the enlightened man subject to the law of causation?&#39;</p>

                    <p>Hyakujo said: &#39;The enlightened man is one with the law of causation.&#39;</p>

                    <p>At the words of Hyakujo the old man was enlightened. &#39;I am emancipated,&#39; he said, paying homage with a deep bow. &#39;I am no more a fox, but I have to leave my body in my dwelling place behind this mountain. Please perform my funeral as a monk.&#39; The he disappeared.</p>

                    <p>The next day Hyakujo gave an order through the chief monk to prepare to attend the funeral of a monk. &#39;No one was sick in the infirmary,&#39; wondered the monks. &#39;What does our teacher mean?&#39;</p>

                    <p>After dinner Hyakujo led the monks out and around the mountain. In a cave, with his staff he poked out the corpse of an old fox and then performed the ceremony of cremation.</p>

                    <p>That evening Hyakujo gave a talk to the monks and told this story about the law of causation.</p>

                    <p>Obaku, upon hearing this story, asked Hyakujo: &#39;I understand that a long time ago because a certain person gave a wrong Zen answer he became a fox for five hundred rebirths. Now I was to ask: If some modern master is asked many questions, and he always gives the right answer, what will become of him?&#39;</p>

                    <p>Hyakujo said: &#39;You come here near me and I will tell you.&#39;</p>

                    <p>Obaku went near Hyakujo and slapped the teacher&#39;s face with this hand, for he knew this was the answer his teacher intended to give him.</p>

                    <p>Hyakujo clapped his hands and laughed at the discernment. &#39;I thought a Persian had a red beard,&#39; he said, &#39;and now I know a Persian who has a red beard.&#39;</p>

                    <p><em>Mumon&#39;s comment:</em> &#39;The enlightened man is not subject.&#39; How can this answer make the monk a fox?<br />
                    &#39;The enlightened man is at one with the law of causation.&#39; How can this answer make the fox emancipated?<br />
                    To understand clearly one has to have just one eye.</p>

                    <p><em>Controlled or not controlled?<br />
                    The same dice shows two faces.<br />
                    Not controlled or controlled,<br />
                    Both are a grievous error.</em></p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Gutei's Finger"
            koan="""
                    <p>Gutei raised his finger whenever he was asked a question about Zen. A boy attendant began to imitate him in this way. When anyone asked the boy what his master had preached about, the boy would raise his finger.</p>

                    <p>Gutei heard about the boy&#39;s mischief. He seized him and cut off his finger. The boy cried and ran away. Gutei called and stopped him. When the boy turned his head to Gutei, Gutei raised up his own finger. In that instant the boy was enlightened.</p>

                    <p>When Gutei was about to pass from this world he gathered his monks around him. &#39;I attained my finger-Zen,&#39; he said, &#39;from my teacher Tenryu, and in my whole life I could not exhaust it.&#39; Then he passed away.</p>

                    <p><em>Mumon&#39;s comment:</em> Enlightenment, which Gutei and the boy attained, has nothing to do with a finger. If anyone clings to a finger, Tenryu will be so disappointed that he will annihilate Gutei, the boy and the clinger all together.</p>

                    <p><em>Gutei cheapens the teaching of Tenryu,<br />
                    Emancipating the boy with a knife.<br />
                    Compared to the Chinese god who pushed aside a mountain with one hand<br />
                    Old Gutei is a poor imitator.</em></p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="A Beardless Foreigner"
            koan="""
                    <p>Wakun complained when he saw a picture of the bearded Bodhidharma: &#39;Why hasn&#39;t that fellow a beard?&#39;</p>

                    <p><em>Mumon&#39;s comment:</em> If you want to study Zen, you must it with your heart. When you attain realization, it must be true realization. You yourself must have the face of the great Bodhidharma to see him. Just once such glimpse will be enough. But if you say you met him, you never saw him at all.</p>

                    <p><em>One should not discuss a dream<br />
                    In front of a simpleton.<br />
                    Why has Bodhidharma no beard?<br />
                    What an absurd question!</em></p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Kyogen Mounts the Tree"
            koan="""
                    <p>Kyogen said: &#39;Zen is like a man hanging in a tree by his teeth over a precipice. His hands grasp no branch, his feet rest on no limb, and under the three another person asks him: &#39;Why does Bodhidharma come to China from India?&#39;</p>

                    <p>&#39;If the man in tree does not answer, he fails; and if he does answer, he falls and loses his life. Now what shall he do?&#39;</p>

                    <p><em>Mumon&#39;s Comment:</em> In such a predicament the most talented eloquence is no use. If you have memorized all the sutras, you cannot use them. When you can give the right answer, even though your past road was one of death, you open up a new road of life. But if you cannot answer, you should ages hence and the future Buddha, Maitreya.</p>

                    <p><em>Kyogen is truly a fool<br />
                    Spreading that ego-killing poison<br />
                    That closes his pupils&#39; mouths<br />
                    And lets their tears stream from their dead eyes.</em></p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Buddha Twirls a Flower"
            koan="""
                    <p>When Buddha was in Grdhrakuta (Vulture Peak) mountain he turned a flower in his fingers and held in before his listeners. Every one was silent. Only Maha-Kashapa smiled at this revelation, although he tried to control the lines of his face.</p>

                    <p>Buddha said: &#39;I have the eye of the true teaching, the heart of Nirvana, the true aspect of non-form, and the ineffable stride of Dharma. It is not expressed by words, but especially transmitted beyond teaching. This teaching I have given to Maha-Kashapa.&#39;</p>

                    <p><em>Mumon&#39;s Comment:</em> Golden-faced Guatama thought he could cheat anyone. He made the good listeners as bad, and sold dog meat under the sign of mutton. And he himself thought it was wonderful. What if all the audience had laughed together? How could he have transmitted the teaching? And again, if Maha-Kashapa had not smiled, how could he have transmitted the teaching? If he says that realization can be transmitted, he is like the city slicker that cheats the country dub, and if he says it cannot be transmitted, why does he approve of Maha-Kashapa?</p>

                    <p><em>At the turning of a flower<br />
                    His disguise was exposed.<br />
                    No one is heaven or earth can surpass<br />
                    Maha-Kashapa&#39;s wrinkled face.</em></p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Joshu Washes the Bowl"
            koan="""
                    <p>A monk told Joshu: &#39;I have just entered the monastery. Please teach me.&#39;</p>

                    <p>Joshu asked: &#39;Have you eaten your rice porridge?&#39;</p>

                    <p>The monk replied: &#39;I have eaten.&#39;</p>

                    <p>Joshu said: &#39;Then you had better wash your bowl.&#39;</p>

                    <p>At that moment the monk was enlightened.</p>

                    <p><em>Mumon&#39;s Comment:</em> Joshu is the man who opens his mouth and shows his heart. I doubt if this monk really saw Joshu&#39;s heart. I hope he did not mistake the bell for a pitcher.</p>

                    <p><em>It is too clear and so it is hard to see.<br />
                    A dunce once searched for fire with a lighted lantern.<br />
                    Had he known what fire was,<br />
                    He could have cooked his rice much sooner.</em></p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Keichu's Wheel"
            koan="""
                    <p>Getsuan said to this students: &#39;Keichu, the first wheel-maker of China, made two wheels of fifty spokes each. Now, suppose you removed the nave uniting the spokes. What would become of the wheel? And had Keichu done this, could he be called the master wheel-maker?&#39;</p>

                    <p><em>Mumon&#39;s Comment:</em> If anyone can answer this question instantly, his eyes will be like a comet and his mind like a flash of lightning.</p>

                    <p><em>When the hub-less wheel turns,<br />
                    Master or no master can stop it.<br />
                    It turns above heaven and below earth,<br />
                    South, north, east and west.</em></p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Seizei Alone and Poor"
            koan="""
                    <p>A monk named Seizei asked of Sozan: &#39;Seizei is alone and poor. Will you give him support?&#39;</p>

                    <p>Sozan asked: &#39;Seizei?&#39;</p>

                    <p>Seizei responded: &#39;Yes, sir.&#39;</p>

                    <p>Sozan said: &#39;You have Zen, the best wine in China, and alrady have finished three cups, and still you are saying that they did not even wet your lips.&#39;</p>

                    <p><em>Mumon&#39;s Comment:</em> Seizei overplayed his hand. Why was it so? Because Sozan had eyes and knew whom to deal. Even so, I want to ask: At what point did Seizei drink wine?</p>

                    <p><em>The poorest man in China,<br />
                    The bravest man in China,<br />
                    He barely sustains himself,<br />
                    Yet wishes to rival the wealthiest.</em></p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Joshu Examines a Monk in Meditation"
            koan="""
                    <p>Joshu went to a place were a monk had retired to meditate and asked him: &#39;What is, is what?&#39;</p>

                    <p>The monk raised his fist.</p>

                    <p>Joshu replied: &#39;Ships cannot remain where the water is too shallow.&#39; And he left.</p>

                    <p>A few days later Joshu went again to visit the monk and asked the same question.</p>

                    <p>The monk answered the same way.</p>

                    <p>Joshu said: &#39;Well given, well taken, well killed, well save.&#39; And he bowed to the monk.</p>

                    <p><em>Mumon&#39;s Comment:</em> The raised fist was the same both times. Why is it Joshu did not admit the first and approved the second one? Where is the fault?</p>

                    <p>Whoever answers this knows that Joshu&#39;s tongue has no bone so he can use it freely. Yet perhaps Joshu is wrong. Or, through that monk, he may have discovered his mistake.</p>

                    <p>If anyone thinks that the one&#39;s insight exceeds the other&#39;s, he has no eyes.</p>

                    <p><em>The light of the eyes is as a comet,<br />
                    And Zen&#39;s activity is as lightning.<br />
                    The sword that kills the man<br />
                    Is the sword that saves the man.</em></p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Zuigan Calls His Own Master"
            koan="""
                    <p>Zuigan called out to himself every day: &#39;Master.&#39;</p>

                    <p>Then he answered himself: &#39;Yes, sir.&#39;</p>

                    <p>And after that he added: &#39;Become sober.&#39;</p>

                    <p>Again he answered: &#39;Yes, sir.&#39;</p>

                    <p>&#39;And after that,&#39; he continued, &#39;do not be deceived by others.&#39;</p>

                    <p>&#39;Yes, sir; yes, sir,&#39; he answered.</p>

                    <p><em>Mumon&#39;s Comment:</em> Old Zuigan sells out and buys himself. He is opening a puppet show. He uses one mask to call &#39;Master&#39; and another that answers the master. Another mask says &#39;Sober up&#39; and another, &#39;Don&#39;t be cheated by others.&#39; If anyone clings to any of his masks, he is mistaken, yet if he imitates Zuigan, he will make himself fox-like.</p>

                    <p><em>Some Zen students do not realize the true man in a mask<br />
                    Because they recognize ego-soul.<br />
                    Ego-soul is the seed of birth and death,<br />
                    And foolish people call it the true man.</em></p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Tokusan Holds His Bowl"
            koan="""
                    <p>Tokusan went to the dining room from the meditation hall holding his bowl. Seppo was on duty cooking. When he met Tokusan he said: &#39;The dinner drum is not yet beaten. Where are you going with your bowl?&#39;</p>

                    <p>So Tokusan returned to his room.</p>

                    <p>Seppo told Ganto about this. Ganto said: &#39;Old Tokusan did not understand the ultimate truth.&#39;</p>

                    <p>Tokusan heard of this remark and asked Ganto to come to him. &#39;I have heard,&#39; he said, &#39;you are not approving my Zen.&#39; Ganto admitted this indirectly. Tokusan said nothing.</p>

                    <p>The next day Tokusan delivered an entirely different kind of lecture to the monks. Ganto laughed and clapped his hands, saying: &#39;I see our old man understands the ultimate truth indeed. None in China can surpass him.&#39;</p>

                    <p><em>Mumon&#39;s Comment:</em> Speaking about ultimate truth, both Ganto and Tokusan did not even dream it. After all, they are dummies.</p>

                    <p><em>Whoever understands the first truth<br />
                    Should understand the ultimate truth.<br />
                    The last and first,<br />
                    Are they not the same?</em></p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Nansen Cuts the Cat in Two"
            koan="""
                    <p>Nansen saw the monks of the eastern and western halls fighting over a cat. He seized the cat and told the monks: &#39;If any of you say a good word, you can save the cat.&#39;</p>

                    <p>No one answered. So Nansen boldly cut the cat in two pieces.</p>

                    <p>That evening Joshu returned and Nansen told him about this. Joshu removed his sandals and, placing them on his head, walked out.</p>

                    <p>Nansen said: &#39;If you had been there, you could have saved the cat.&#39;</p>

                    <p><em>Mumon&#39;s Comment:</em> Why did Joshu put his sandals on his head? If anyone answers this question, he will understand exactly how Nansen enforced the edict. If not, he should watch his own head.</p>

                    <p><em>Had Joshu been there,<br />
                    He would have enforced the edict oppositely.<br />
                    Joshua snatches the sword<br />
                    And Nansen begs for his life.</em></p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Tozan's Three Blows"
            koan="""
                    <p>Tozan went to Ummon. Ummon asked him where he had come from.</p>

                    <p>Tozan said: &#39;From Sato village.&#39;</p>

                    <p>Ummon asked: &#39;In what temple did you remain for the summer?&#39;</p>

                    <p>Tozan replied: &#39;The temple of Hoji, south of the lake.&#39;</p>

                    <p>&#39;When did you leave there?&#39; asked Ummon, wondering how long Tozan would continue with such factual answers.</p>

                    <p>&#39;The twenty-fifth of August,&#39; answered Tozan.</p>

                    <p>Ummon said: &#39;I should give you three blows with a stick, but today I forgive you.&#39;</p>

                    <p>The next day Tozan bowed to Ummon and asked: &#39;Yesterday you forgave me three blows. I do not know why you thought me wrong.&#39;</p>

                    <p>Ummon, rebuking Tozan&#39;s spiritless responses, said: &#39;You are good for nothing. You simply wander from one monastery to another.&#39;</p>

                    <p>Before Ummon&#39;s words were ended Tozan was enlightened.</p>

                    <p><em>Mumon&#39;s Comment:</em> Ummon fed Tozan good Zen food. If Tozan can digest it, Ummon may add another member to his family.</p>

                    <p>In the evening Tozan swam around in a sea of good and bad, but at dawn Ummon crushed his nut shell. After all, he wasn&#39;t so smart.</p>

                    <p>Now, I want to ask: Did Tozan deserve the three blows? If you say yes, not only Tozan but every one of you deserves them. If you say no, Ummon is speaking a lie. If you answer this question clearly, you can eat the same food as Tozan.</p>

                    <p><em>The lioness teaches her cubs roughly;<br />
                    The cubs jump and she knocks them down.<br />
                    When Ummon saw Tozan his first arrow was light;<br />
                    His second arrow shot deep.</em></p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Bells and Robes"
            koan="""
                    <p>Ummon asked: &#39;The world is such a wide world, why do you answer a bell and don ceremonial robes?&#39;</p>

                    <p><em>Mumon&#39;s Comment:</em> When one studies Zen one need not follow sound or colour or form. Even though some have attained insight when hearing a voice or seeing a colour or a form, this is a very common way. It is not true Zen. The real Zen student controls sound, colour, form, and actualizes the truth in his everyday life.</p>

                    <p>Sound comes to the ear, the ear goes to the sound. When you blot out sound and sense, what do you understand? While listening with ears one never can understand. To understand intimately one should see sound.</p>

                    <p><em>When you understand, you belong to the family;<br />
                    When you do not understand, you are a stranger.<br />
                    Those who do not understand belong to the family,<br />
                    And when they understand they are strangers.</em></p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="The Three Calls of the Emperor's Teacher"
            koan="""
                    <p>Chu, called Kokushi, the teacher of the emperor, called to his attendant: &#39;Oshin.&#39;</p>

                    <p>Oshin answered: &#39;Yes.&#39;</p>

                    <p>Chu repeated, to test his pupil: &#39;Oshin.&#39;</p>

                    <p>Oshin repeated: &#39;Yes.&#39;</p>

                    <p>Chu called: &#39;Oshin.&#39;</p>

                    <p>Oshin answered: &#39;Yes.&#39;</p>

                    <p>Chu said &#39;I ought to apologize for you for all this calling, but really you ought to apologize to me.&#39;</p>

                    <p><em>Mumon&#39;s Comment:</em> When Old Chu called Oshin three times his tongue was rotting, but when Oshin answered three times his words were brilliant. Chu was getting decrepit and lonesome, and his method of teaching was like holding a cow&#39;s head to feed it clover.</p>

                    <p>Oshin did not trouble to show his Zen either. His satisfied stomach had no desire to feast. When the country is prosperous everyone is indolent; when the home is wealthy the children are spoiled.</p>

                    <p>Now I want to ask you: Which one should apologize?</p>

                    <p><em>When prison stocks are iron and have no place for the head, the prisoner is doubly in trouble.<br />
                    When there is no place for Zen in the head of our generation, it is in grievous trouble.<br />
                    If you try to hold up the gate and door of a falling house,<br />
                    You also will be in trouble.</em></p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Tozan's Three Pounds"
            koan="""
                    <p>A monk asked Tozan when he was weighing some flax: &#39;What is Buddha?&#39;</p>

                    <p>Tozan said: &#39;This flax weighs three pounds.&#39;</p>

                    <p><em>Mumon&#39;s Comment:</em> Old Tozan&#39;s Zen is like a clam. The minute the shell opens you see the whole inside. However, I want to ask you: Do you see the real Tozan?</p>

                    <p><em>Three pounds of flax in front of your nose,<br />
                    Close enough, and mind is still closer.<br />
                    Whoever talks about affirmation and negation<br />
                    Lives in the right and wrong region.</em></p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Everyday Life is the Path"
            koan="""
                    <p>Joshu asked Nansen: &#39;What is the path?&#39;</p>

                    <p>Nansen said: &#39;Everyday life is the path.&#39;</p>

                    <p>Joshu asked: &#39;Can it be studied?&#39;</p>

                    <p>Nansen said: &#39;If you try to study, you will be far away from it.&#39;</p>

                    <p>Joshu asked: &#39;If I do not study, how can I know it is the path?&#39;</p>

                    <p>Nansen said: &#39;The path does not belong to the perception world, neither does it belong to the nonperception world. Cognition is a delusion and noncognition is senseless. If you want to reach the true path beyond doubt, place yourself in the same freedom as sky. You name it neither good nor not-good.&#39;</p>

                    <p>At these words Joshu was enlightened.</p>

                    <p><em>Mumon&#39;s Comment:</em> Nansen could met Joshu&#39;s frozen doubts at once when Joshu asked his questions. I doubt that if Joshu reached the point that Nansen did. He needed thirty more years of study.</p>

                    <p><em>In spring, hundreds of flowers; in autumn, a harvest moon;<br />
                    In the summer, a refreshing breeze; in winter snow will accompany your.<br />
                    If useless things do not hang in your mind,<br />
                    Any season is a good season for you.</em></p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="The Enlightened Man"
            koan="""
                    <p>Shogen asked: &#39;Why does the enlightened man not stand on his feet and explain himself?&#39; And he also said: &#39;It is not necessary for speech to come from the tongue.&#39;</p>

                    <p><em>Mumon&#39;s Comment:</em> Shogen spoke plainly enough, but how many will understand? If anyone comprehends, he should come to my place and test out my big stick. Why, look here, to test real gold you must see it through fire.</p>

                    <p><em>If the feet of enlightenment moved, the great ocean would overflow;<br />
                    If that head bowed, it would look down upon the heavens.<br />
                    Such a body hsa no place to rest...<br />
                    Let another continue this poem.</em></p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Dried Dung"
            koan="""
                    <p>A monk asked Ummon: &#39;What is Buddha?&#39; Ummon answered him: &#39;Dried dung.&#39;</p>

                    <p><em>Mumon&#39;s Comment:</em> It seems to me Ummon is so poor he cannot distinguish the taste of one food from another, or else he is too busy to write readable letters. Well, he tried to hold his school with dried dung. And his teaching was just as useless.</p>

                    <p><em>Lightning flashes,<br />
                    Sparks shower.<br />
                    In one blink of your eyes<br />
                    You have missed seeing.</em></p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Kashapa's Preaching Sign"
            koan="""
                    <p>Anada asked Kashapa: &#39;Buddha gave you the golden-woven robe of successorship. What else did he give you?&#39;</p>

                    <p>Kashapa said: &#39;Ananda.&#39;</p>

                    <p>Ananda answered: &#39;Yes, brother.&#39;</p>

                    <p>Said Kashapa: &#39;Now you can take down my preaching sign and put up your own.&#39;</p>

                    <p><em>Mumon&#39;s Comment:</em> If one understands this, he will see the old brotherhood still gathering, but if not, even though he has studied the truth from ages before the Buddhas, he will not attain enlightenment.</p>

                    <p><em>The point of the question is dull but the answer is intimate.<br />
                    How many persons hearing it will open their eyes?<br />
                    Elder brother calls and younger brother answers,<br />
                    This spring does not belong to the ordinary season.</em></p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Do Not Think Good, Do Not Think Not-Good"
            koan="""
                    <p>When he became emancipated the sixth patriarch received from the fifth patriarch the bowl and robe given from the Buddha to his successors, generation after generation.</p>

                    <p>A monk named E-myo out of envy pursued the patriarch to take this great treasure away from him. The sixth patriarch placed the bowl and robe on a stone in the road and told E-myo: &#39;These objects just symbolize the faith. There is no use fighting over them. If you desire to take them, take them now.&#39;</p>

                    <p>When E-myo went to move the bowl and robe they were as heavy as mountains. He could not budge them. Trembling for shame he said: &#39;I came wanting the teaching, not the material treasures. Please teach me.&#39;</p>

                    <p>The sixth patriarch said: &#39;When you do not think good and when you do not think not-good, what is your true self?&#39;</p>

                    <p>At these words E-myo was illumined. Perspiration broke out all over his body. He cried and bowed, saying: &#39;You have given me the secret words and meanings. Is there yet a deeper part of the teaching?&#39;</p>

                    <p>The sixth patriarch replied: &#39;What I have told you is no secret at all. When you realize your true self the secret belongs to you.&#39;</p>

                    <p>E-myo said: &#39;I was under the fifth patriarch for many years but could not realize my true self until now. Through your teaching I find the source. A person drinks water and knows himself whether it is cold or warm. May I call you my teacher?&#39;</p>

                    <p>The sixth patriarch replied: &#39;We studied together under the fifth patriarch. Call him your teacher, but just treasure what you have attained.&#39;</p>

                    <p><em>Mumon&#39;s Comment:</em> The sixth patriarch certainly was kind in such an emergency. If was as if he removed the skin and seeds from the fruit and then, opening the pupil&#39;s mouth, let him eat.</p>

                    <p><em>You cannot describe it, you cannot picture it,<br />
                    You cannot admire it, you cannot sense it.<br />
                    It is your true self, it has nowhere to hide.<br />
                    When the world is destroyed, it will no be destroyed.</em></p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Without Words, Without Silence"
            koan="""
                    <p>A monk asked Fuketsu: &#39;Without speaking, without silence, how can you express the truth?&#39;</p>

                    <p>Fuketsu observed: &#39;I always remember spring-time in southern China. The birds sing among innumerable kinds of fragrant flowers.&#39;</p>

                    <p><em>Mumon&#39;s Comment:</em> Fuketsu used to have lightning Zen. Whenever he had the opportunity, he flashed it. But this time he failed to do so and only borrowed from an old Chinese poem. Never mind Fuketsu&#39;s Zen. If you want to express the truth, throw out your words, throw out your silence, and tell me about your own Zen.</p>

                    <p><em>Without revealing his own penetration,<br />
                    He offered another&#39;s words, not his to give.<br />
                    Had he chattered on and on,<br />
                    Even his listeners would have been embarassed.</em></p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Preaching from the Third Seat"
            koan="""
                    <p>In a dream Kyozen went to Maitreya&#39;s Pure Land. He recognized himself seated in the third seat in the abode of Maitreya. Someone announced: &#39;Today the one who sits in the third seat will preach.&#39;</p>

                    <p>Kyozen arose and, hitting the gavel, said: &#39;The truthof Mahayana teaching is transcendent, above words and thought. Do you understand?&#39;</p>

                    <p><em>Mumon&#39;s Comment:</em> I want to ask you monks: Did he preach or did he not?</p>

                    <p>When he opens his mouth he is lost. When he seals his mouth he is lost. If he does not open it, if he does not seal it, he is 108,000 miles from the truth.</p>

                    <p><em>In the light of day,<br />
                    Yet in a dream he talks of a dream.<br />
                    A monster among monsters,<br />
                    He intended to deceive the whole crowd.</em></p>

                    <h2>&nbsp;</h2>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Two Monks Rolls Up the Screen"
            koan="""
                    <p>Hogen of Seiryo monastery was about to lecture before dinner when he noticed that the bamboo screen lowered for meditation had not been rolled up. He pointed to it. Two monks arose from the audience and rolled it up.</p>

                    <p>Hogen, observing the physical moment, said: &#39;The state of the first monk is good, not that of the other.&#39;</p>

                    <p><em>Mumon&#39;s Comment:</em> I want to ask you: Which of those two monks gained and which lost? If any of you has one eye, he will see the failure on the teacher&#39;s part. However, I am not discussing gain and loss.</p>

                    <p><em>When the screen is rolled up the great sky opens,<br />
                    Yet the sky is not attuned to Zen.<br />
                    It is best to forget the great sky<br />
                    And to retire from every wind.</em></p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="It is Not Mind, It is Not Buddha, It is Not Things"
            koan="""
                    <p>A monk asked Nansen: &#39;Is there a teaching no master ever preached before?&#39;</p>

                    <p>Nansen said: &#39;Yes, there is.&#39;</p>

                    <p>&#39;What is it?&#39; asked the monk.</p>

                    <p>Nansen replied: &#39;It is not mind, it is not Buddha, it is not things.&#39;</p>

                    <p><em>Mumon&#39;s Comment:</em> Old Nansen gave away his treasure-words. He must have been greatly upset.</p>

                    <p><em>Nansen was too kind and lost his treasure.<br />
                    Truly, words have no power.<br />
                    Even though the mountain becomes the sea,<br />
                    Words cannot open another&#39;s mind.</em></p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Blow Out the Candle"
            koan="""
                    <p>Tokusan was studying Zen under Ryutan. One night he came to Ryutan and asked many questions. The teacher said: &#39;The night is getting old. Why don&#39;t you retire?&#39;</p>

                    <p>So Tukusan bowed and opened the screen to go out, observing: &#39;It is very dark outside.&#39;</p>

                    <p>Ryutan offered Tokusan a lighted candle to find his way. Just as Tokusan received it, Ryutan blew it out. At that moment the mind of Tokusan was opened.</p>

                    <p>&#39;What have you attained?&#39; asked Ryutan.</p>

                    <p>&#39;From now on,&#39; said Tokusan, &#39;I will not doubt the teacher&#39;s words.&#39;</p>

                    <p>The next day Ryutan told the monks at his lecture: &#39;I see one monk among you. His teeth are like the sword tree, his mouth is like the blood bowl. If you hit him hard with a big stick, he will not even so much as look back at you. Someday he will mount the highest peak and carry my teaching there.&#39;</p>

                    <p>On that day, in front of the lecture hall, Tokusan burned to ashes his commentaries on the sutras. He said: &#39;However abstruse the teachings are, in comparison with this enlightenment they are like a single hair to the great sky. However profound the complicated knowledge of the world, compared to this enlightenment it is like one drop of water to the great ocean.&#39; Then he left the monastery.</p>

                    <p><em>Mumon&#39;s Comment:</em> When Tokusan was in his own country he was not satisfied with Zen although he had heard about it. He thought: &#39;Those Southern monks say they can teach Dharma outside of the sutras. They are all wrong. I must teach them.&#39; So he travelled south. He happened to stop near Ryutan&#39;s monastery for refreshments. An old woman who was there asked him: &#39;What are you carrying so heavily?&#39;</p>

                    <p>Tokusan replied: &#39;This is a commentary I have made on the Diamond Sutra after many years of work.&#39;</p>

                    <p>The old woman said: &#39;I read that sutra which says: &quot;The past mind cannot be held, the present mind cannot be held.&quot; You wish some tea and refreshments. Which mind do you propose to use for them?&#39;</p>

                    <p>Tokusan was as though dumb. Finally he asked the woman: &#39;Do you know of any good teacher around here?&#39;</p>

                    <p>The old woman referred him to Ryutan, not more than five miles away. So he went to Ryutan in all humility, quite different from when he had started his journey. Ryutan in turn was so kind he forgot his own dignity. It was like pouring muddy water over a drunken man to sober him. After all, it was an unnecessary comedy.</p>

                    <p><em>A hundred hearings cannot surpass one seeing,<br />
                    But after you see the teacher, that once glance cannot surpass a hundred hearings.<br />
                    His nose was very high<br />
                    But he was blind after all.</em></p>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Not the Wind, Not the Flag"
            koan="""
                    <p>Two monks were arguing about a flag. One said: &#39;The flag is moving.&#39;</p>

                    <p>The other said: &#39;The wind is moving.&#39;</p>

                    <p>The sixth patriarch happened to be passing by. He told them: &#39;Not the wind, not the flag; mind is moving.&#39;</p>

                    <p><em>Mumon&#39;s Comment:</em> The sixth patriarch said: &#39;The wind is not moving, the flag is not moving. Mind is moving.&#39; What did he mean? If you understand this intimately, you will see the two monks there trying to buy iron and gaining gold. The sixth patriarch could not bear to see those two dull heads, so he made such a bargain.</p>

                    <p><em>Wind, flag, mind moves.<br />
                    The same understanding.<br />
                    When the mouth opens<br />
                    All are wrong.</em></p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="Joshu Investigates"
            koan="""
                    <p>A travelling monk asked an old woman the road to Taizan, a popular temple supposed to give wisdom to the one who worships there. The old woman said: &#39;Go straight ahead.&#39; When the monk proceeded a few steps, she said to herself: &#39;He also is a common church-goer.&#39;</p>

                    <p>Someone told this incident to Joshu, who said: &#39;Wait until I investigate.&#39; The next day he went and asked the same question, and the old woman gave the same answer.</p>

                    <p>Joshu remarked: &#39;I have investigated that old woman.&#39;</p>

                    <p><em>Mumon&#39;s Comment:</em> The old woman understood how war is planned, but she did not know how spies sneak in behind her tent. Old Joshu played the spy&#39;s work and turned the tables on her, but he was not an able general. Both had their faults. Now I want to ask you: What was the point of Joshu&#39;s investigating the old woman?</p>

                    <p><em>When the question is common<br />
                    The answer is also common.<br />
                    When the question is sand in a bowl of boiled rice<br />
                    The answer is a stick in the soft mud.</em></p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }

        addNewKoan {
            title="This Mind is Buddha"
            koan="""
                    <p>Daibai asked Baso: &#39;What is Buddha?&#39;</p>

                    <p>Baso said: &#39;This mind is Buddha.&#39;</p>

                    <p><em>Mumon&#39;s Comment:</em> If anyone wholly understands this, he is wearing Buddha&#39;s clothing, he is eating Buddha&#39;s food, he is speaking Buddha&#39;s words, he is behaving as Buddha, he is Buddha.</p>

                    <p>This anecdote, however, has given many pupil the sickness of formality. If one truly understands, he will wash out his mouth for three days after saying the word Buddha, and he will close his ears and flee after hearing &#39;This mind is Buddha.&#39;</p>

                    <p><em>Under blue sky, in bright sunlight,<br />
                    One need not search around.<br />
                    Asking what Buddha is<br />
                    Is like hiding loot in one&#39;s pocket and declaring oneself innocent.</em></p>

                    <h2>&nbsp;</h2>

                """.trimIndent()
            //koanImagePath=""
        }
        return arrKoans
    }


}
