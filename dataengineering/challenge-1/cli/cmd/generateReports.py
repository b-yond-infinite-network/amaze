from cli.db import Session, engine, Base
from sqlalchemy import func,desc,distinct,union_all,text
from cli.models.Athletes import Athletes
from cli.models.Countries import Countries
from cli.models.Country_stats import Country_stats
from cli.models.Summer_games import Summer_games
from cli.models.Winter_games import Winter_games



#Reports Functions

#Create a base report querying the summer games showing the total number of athletes of the top 3 sports
def summer_base_report(session):
    
    summer_base_report = session.query(Summer_games.sport, func.count(Summer_games.sport).label('athletes_c')
        ).group_by(Summer_games.sport).order_by(
            desc('athletes_c')).limit(3).all()
    #print(summer_base_report)
    return summer_base_report

#Create a report that shows every sport's number of unique events and unique athletes
def sport_number_report(session):
    report_summer = session.query(Summer_games.sport,func.count(distinct(Summer_games.event)).label('events_c'),
    func.count(distinct(Summer_games.athlete_id)).label('athlete_id_c')).group_by(Summer_games.sport).all()

    report_winter = session.query(Winter_games.sport,func.count(distinct(Winter_games.event)).label('events_c'),
    func.count(distinct(Winter_games.athlete_id)).label('athlete_id_c')).group_by(Winter_games.sport).all()
    #print(report_winter + report_summer)
    return report_winter + report_summer


#Create a report that shows the age of the oldest athlete by region
def oldest_region_report(session):
    oldest_region_report = session.execute(text('''
    SELECT 
        region, MAX(age) as age_max
    FROM
        (SELECT 
            a.*, athletes.name, athletes.age, countries.region
        FROM
            (SELECT DISTINCT
            (athlete_id), country_id
        FROM
            summer_games UNION SELECT DISTINCT
            (athlete_id), country_id
        FROM
            winter_games) a
        JOIN athletes ON (athletes.id = a.athlete_id)
        JOIN countries ON (a.country_id = countries.id)
        ORDER BY athletes.age DESC) b
    GROUP BY b.region'''))
    
    return oldest_region_report.mappings().all()

#Create a report that shows the unique number of events held for each sport on both
#winter and summer games, and order them from the most number of events to the least number of events.
def sport_event_report(session):
    sport_event_report = session.execute(text('''
    SELECT 
        a.sport, COUNT(a.event) as event_c
    FROM
        (SELECT 
            sport, event
        FROM
            (SELECT 
            sport, event
        FROM
            summer_games UNION SELECT 
            sport, event
        FROM
            winter_games) sports_events
        GROUP BY sport , event
        ORDER BY 2 DESC) a
    GROUP BY a.sport
    ORDER BY 2 desc'''))

    #print(sport_event_report.mappings().all())
    return sport_event_report.mappings().all()


#General reports
def generate_reports():
    session = Session()
    
    print('''Summer Base Report :\n Base report querying the summer games showing the total number of athletes of the top 3 sports \n''')
    summer_base_report(session)

    print('''sport_number_report :\n Report that shows every sport's number of unique events and unique athletes \n''')
    sport_number_report(session)

    print('''oldest_region_report :\n Report that shows the age of the oldest athlete by region \n''')
    oldest_region_report(session)

    print('''sport_event_report :\n Report that shows the unique number of events held for each sport on both
#winter and summer games, and order them from the most number of events to the least number of events. \n''')
    sport_event_report(session)



generate_reports()