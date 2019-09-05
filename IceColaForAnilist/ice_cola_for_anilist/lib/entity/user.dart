import 'dart:ui';
import 'data.dart';

class User {
  String id;
  String name;
  String avatar;
  String tag;
  Color tagBackground;
  String overview;
  List<Anime> animeList;
  List<Manga> mangaList;
  List<PageData> favorites;
  List<UserStat> stats;
  List<User> social;
  List<Review> reviews;
//  List<Submission> submissions; // TODO: pending now
}

class UserStat {
  String category;
  double meanScore;
  double standardDeviation;
}

class AnimeStat extends UserStat {
  int epsRead;
  double daysRead;
  double daysPlanned;
}

class MangaStat extends UserStat {
  int chsRead;
  double volsRead;
  double chsPlanned;
}