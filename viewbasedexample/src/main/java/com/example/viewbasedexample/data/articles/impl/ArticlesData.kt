@file:Suppress("ktlint:max-line-length")
package com.example.viewbasedexample.data.articles.impl

import com.example.viewbasedexample.R
import com.example.viewbasedexample.model.Article
import com.example.viewbasedexample.model.ArticlesFeed
import com.example.viewbasedexample.model.Metadata
import com.example.viewbasedexample.model.Paragraph
import com.example.viewbasedexample.model.ParagraphType
import com.poool.access.compose.PaywallMode

val paragraphsArticle = listOf(
    Paragraph(
        ParagraphType.Header,
        "Haec dum oriens diu perferret"
    ),
    Paragraph(
        ParagraphType.Text,
        "Illud autem non dubitatur quod cum esset aliquando virtutum omnium domicilium Roma, ingenuos advenas plerique nobilium, ut Homerici bacarum suavitate Lotophagi, humanitatis multiformibus officiis retentabant."
    ),
    Paragraph(
        ParagraphType.Text,
        "Cyprum itidem insulam procul a continenti discretam et portuosam inter municipia crebra urbes duae faciunt claram Salamis et Paphus, altera Iovis delubris altera Veneris templo insignis. tanta autem tamque multiplici fertilitate abundat rerum omnium eadem Cyprus ut nullius externi indigens adminiculi indigenis viribus a fundamento ipso carinae ad supremos usque carbasos aedificet onerariam navem omnibusque armamentis instructam mari committat."
    ),
    Paragraph(
        ParagraphType.Text,
        "Cyprum itidem insulam procul a continenti discretam et portuosam inter municipia crebra urbes duae faciunt claram Salamis et Paphus, altera Iovis delubris altera Veneris templo insignis. tanta autem tamque multiplici fertilitate abundat rerum omnium eadem Cyprus ut nullius externi indigens adminiculi indigenis viribus a fundamento ipso carinae ad supremos usque carbasos aedificet onerariam navem omnibusque armamentis instructam mari committat."
    ),
    Paragraph(
        ParagraphType.Text,
        "Sed ut tum ad senem senex de senectute, sic hoc libro ad amicum amicissimus scripsi de amicitia. Tum est Cato locutus, quo erat nemo fere senior temporibus illis, nemo prudentior; nunc Laelius et sapiens (sic enim est habitus) et amicitiae gloria excellens de amicitia loquetur. Tu velim a me animum parumper avertas, Laelium loqui ipsum putes. C. Fannius et Q. Mucius ad socerum veniunt post mortem Africani; ab his sermo oritur, respondet Laelius, cuius tota disputatio est de amicitia, quam legens te ipse cognosces."
    ),

    Paragraph(
        ParagraphType.Header,
        "Caesar libertatemque superbiam ratus tamquam\n"
    ),
    Paragraph(
        ParagraphType.Text,
        "Haec dum oriens diu perferret, caeli reserato tepore Constantius consulatu suo septies et Caesaris ter egressus Arelate Valentiam petit, in Gundomadum et Vadomarium fratres Alamannorum reges arma moturus, quorum crebris excursibus vastabantur confines limitibus terrae Gallorum.",
    ),
    Paragraph(
        ParagraphType.Text,
        "Cumque pertinacius ut legum gnarus accusatorem flagitaret atque sollemnia, doctus id Caesar libertatemque superbiam ratus tamquam obtrectatorem audacem excarnificari praecepit, qui ita evisceratus ut cruciatibus membra deessent, inplorans caelo iustitiam, torvum renidens fundato pectore mansit inmobilis nec se incusare nec quemquam alium passus et tandem nec confessus nec confutatus cum abiecto consorte poenali est morte multatus. et ducebatur intrepidus temporum iniquitati insultans, imitatus Zenonem illum veterem Stoicum qui ut mentiretur quaedam laceratus diutius, avulsam sedibus linguam suam cum cruento sputamine in oculos interrogantis Cyprii regis inpegit.",
    ),
    Paragraph(
        ParagraphType.Text,
        "Dein Syria per speciosam interpatet diffusa planitiem. hanc nobilitat Antiochia, mundo cognita civitas, cui non certaverit alia advecticiis ita adfluere copiis et internis, et Laodicia et Apamia itidemque Seleucia iam inde a primis auspiciis florentissimae."
    ),

    Paragraph(
        ParagraphType.Header,
        "Sed ut tum ad senem senex de senectute"
    ),
    Paragraph(
        ParagraphType.Text,
        "Cum haec taliaque sollicitas eius aures everberarent expositas semper eius modi rumoribus et patentes, varia animo tum miscente consilia, tandem id ut optimum factu elegit: et Vrsicinum primum ad se venire summo cum honore mandavit ea specie ut pro rerum tunc urgentium captu disponeretur concordi consilio, quibus virium incrementis Parthicarum gentium a arma minantium impetus frangerentur."
    ),
    Paragraph(
        ParagraphType.Text,
        "Itaque verae amicitiae difficillime reperiuntur in iis qui in honoribus reque publica versantur; ubi enim istum invenias qui honorem amici anteponat suo? Quid? Haec ut omittam, quam graves, quam difficiles plerisque videntur calamitatum societates! Ad quas non est facile inventu qui descendant. Quamquam Ennius recte."
    ),
    Paragraph(
        ParagraphType.Text,
        "Rogatus ad ultimum admissusque in consistorium ambage nulla praegressa inconsiderate et leviter proficiscere inquit ut praeceptum est, Caesar sciens quod si cessaveris, et tuas et palatii tui auferri iubebo prope diem annonas. hocque solo contumaciter dicto subiratus abscessit nec in conspectum eius postea venit saepius arcessitus."
    ),
)

val worldArticle = Article(
    id = "dc523f0ed25c",
    title = "Brexit: there could be an american 'attractive' UK trade deal",
    subtitle = "Nihil est enim remuneratione benevolentiae, nihil vicissitudine studiorum officiorumque iucundius. Unde Rufinus ea tempestate praefectus praetorio ad discrimen trusus est ultimum.",
    metadata = Metadata(
        date = "August 02",
        readTimeMinutes = 1
    ),
    paragraphs = paragraphsArticle,
    imageId = R.drawable.world,
    imageThumbId = R.drawable.world_thumb
)

val politicsArticle = Article(
    id = "7446d8dfd7dc",
    title = "A retirement savings plan could be created",
    subtitle = "Nihil est enim remuneratione benevolentiae, nihil vicissitudine studiorum officiorumque iucundius. Unde Rufinus ea tempestate praefectus praetorio ad discrimen trusus est ultimum.",
    metadata = Metadata(
        date = "July 30",
        readTimeMinutes = 3
    ),
    paragraphs = paragraphsArticle,
    imageId = R.drawable.politics,
    imageThumbId = R.drawable.politics_thumb,
    isPremium = true,
    paywallType = PaywallMode.BOTTOM_SHEET
)

val socialArticle = Article(
    id = "ac552dcc1741",
    title = "Over 200000 industrial workers strike in Italy",
    subtitle = "Nihil est enim remuneratione benevolentiae, nihil vicissitudine studiorum officiorumque iucundius. Unde Rufinus ea tempestate praefectus praetorio ad discrimen trusus est ultimum.",
    metadata = Metadata(
        date = "July 09",
        readTimeMinutes = 1
    ),
    paragraphs = paragraphsArticle,
    imageId = R.drawable.social,
    imageThumbId = R.drawable.social_thumb,
    isPremium = true,
    paywallType = PaywallMode.BOTTOM_SHEET
)

val sportsArticle = Article(
    id = "84eb677660d9",
    title = "European Handball Championship creates a sensation",
    subtitle = "Nihil est enim remuneratione benevolentiae, nihil vicissitudine studiorum officiorumque iucundius. Unde Rufinus ea tempestate praefectus praetorio ad discrimen trusus est ultimum.",
    metadata = Metadata(
        date = "April 02",
        readTimeMinutes = 1
    ),
    paragraphs = paragraphsArticle,
    imageId = R.drawable.sports,
    imageThumbId = R.drawable.sports_thumb,
    isPremium = true,
    paywallType = PaywallMode.CUSTOM
)

val opinionArticle = Article(
    id = "55db18283aca",
    title = "Collin's program explained in 10 points",
    subtitle = "Nihil est enim remuneratione benevolentiae, nihil vicissitudine studiorum officiorumque iucundius. Unde Rufinus ea tempestate praefectus praetorio ad discrimen trusus est ultimum.",
    metadata = Metadata(
        date = "July 24",
        readTimeMinutes = 4
    ),
    paragraphs = paragraphsArticle,
    imageId = R.drawable.opinion,
    imageThumbId = R.drawable.opinion_thumb
)

val techArticle = Article(
    id = "55db18283aca",
    title = "This american startup hits 100 million subscribers",
    subtitle = "Nihil est enim remuneratione benevolentiae, nihil vicissitudine studiorum officiorumque iucundius. Unde Rufinus ea tempestate praefectus praetorio ad discrimen trusus est ultimum.",
    metadata = Metadata(
        date = "July 24",
        readTimeMinutes = 4
    ),
    paragraphs = paragraphsArticle,
    imageId = R.drawable.tech,
    imageThumbId = R.drawable.tech_thumb,
    isPremium = true,
    paywallType = PaywallMode.BOTTOM_SHEET
)

val cultureArticle = Article(
    id = "55db18283aca",
    title = "Paris: 'Please do not give us this sculpture'",
    subtitle = "Nihil est enim remuneratione benevolentiae, nihil vicissitudine studiorum officiorumque iucundius. Unde Rufinus ea tempestate praefectus praetorio ad discrimen trusus est ultimum.",
    metadata = Metadata(
        date = "July 24",
        readTimeMinutes = 4
    ),
    paragraphs = paragraphsArticle,
    imageId = R.drawable.culture,
    imageThumbId = R.drawable.culture_thumb
)

val healthArticle = Article(
    id = "55db18283aca",
    title = "CES 2018 is interested in health and wellness",
    subtitle = "Nihil est enim remuneratione benevolentiae, nihil vicissitudine studiorum officiorumque iucundius. Unde Rufinus ea tempestate praefectus praetorio ad discrimen trusus est ultimum.",
    metadata = Metadata(
        date = "July 24",
        readTimeMinutes = 4
    ),
    paragraphs = paragraphsArticle,
    imageId = R.drawable.health,
    imageThumbId = R.drawable.health_thumb,
    isPremium = true,
    paywallType = PaywallMode.BOTTOM_SHEET
)

val financeArticle = Article(
    id = "55db18283aca",
    title = "SMBs turn to accounting outsourcing",
    subtitle = "Nihil est enim remuneratione benevolentiae, nihil vicissitudine studiorum officiorumque iucundius. Unde Rufinus ea tempestate praefectus praetorio ad discrimen trusus est ultimum.",
    metadata = Metadata(
        date = "July 24",
        readTimeMinutes = 4
    ),
    paragraphs = paragraphsArticle,
    imageId = R.drawable.finance,
    imageThumbId = R.drawable.finance_thumb
)

val magazineArticle = Article(
    id = "55db18283aca",
    title = "Prison overpopulation over... surveillance",
    subtitle = "Nihil est enim remuneratione benevolentiae, nihil vicissitudine studiorum officiorumque iucundius. Unde Rufinus ea tempestate praefectus praetorio ad discrimen trusus est ultimum.",
    metadata = Metadata(
        date = "July 24",
        readTimeMinutes = 4
    ),
    paragraphs = paragraphsArticle,
    imageId = R.drawable.magazine,
    imageThumbId = R.drawable.magazine_thumb,
    isPremium = true,
    paywallType = PaywallMode.BOTTOM_SHEET
)

val articles: ArticlesFeed =
    ArticlesFeed(
        highlightedArticle = sportsArticle,
        recommendedArticles = listOf(worldArticle, politicsArticle, socialArticle, financeArticle),
        popularArticles = listOf(
            opinionArticle,
            techArticle,
            cultureArticle,
            healthArticle,
            magazineArticle
        ),
        recentArticles = listOf(
            socialArticle.copy(id = "article8"),
            sportsArticle.copy(id = "article9"),
            opinionArticle.copy(id = "worldArticle0")
        )
    )
