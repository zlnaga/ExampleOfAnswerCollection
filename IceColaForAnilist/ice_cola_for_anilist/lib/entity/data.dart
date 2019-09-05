import 'package:ice_cola_for_anilist/entity/user.dart';

// P0 Structure
class PageData {
  String id;
  String name;
}

//region P1 Structures
class MediaData extends PageData {
  double myScore;
  int format;
  int status;
  DateTime startDate;
  DateTime endDate;
  double averageScore;
  double meanScore;
  int popularity;
  int favorites;
  String source;
  List<Genre> genres;
  Map<int, MediaData> relations;
  Map<String, String> translatedTitle;
}

class EntityData extends PageData {
  int favorites;
}

class SearchableData extends PageData {
  String keyword;
}

class ArticleData extends PageData {
  User author;
  String content; // TODO: This is a rich text.
  List<User> favorites;
  DateTime time;
}
//endregion

//region Media Structures
class Anime extends MediaData {
  int myCurrentProgress;
  int totalEps;
  bool isAiring;
  String airingInfo;
  String epDuration;
  String season;
  Studio studio;
  String hashTag;
}

class Manga extends MediaData {
  int myCurrentChapter;
  int myCurrentVol;
  int totalChapters;
  int totalVols;
  bool isReleasing;
  List<String> synonyms;
}
//endregion

//region Entity Structures
class Character extends EntityData {
  String sourceName;
  String description;
  List<MediaData> medias;
}

class Staff extends EntityData {
  List<String> otherNames;
  String description;
  List<MediaData> medias;
}

class Studio extends EntityData {
  List<MediaData> medias;
}
//endregion

//region Searchable Structures
class Genre extends SearchableData {}

class Tag extends SearchableData {}
//endregion

//region Article Structures
class Comment extends ArticleData {}

class Activity extends ArticleData {
  List<Comment> comments;
  int type; // Text Status / List Progress
}

class Thread extends ArticleData {
  Thread replyTarget;
}

class Forum extends ArticleData {
  List<Thread> replies;
  String category;
  int views;
}

class Review extends ArticleData {
  String summary;
  double score;
  double totalScore;
}
//endregion