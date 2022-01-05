import json
import os
import pandas as pd
import names
import random
from scipy.stats import *

def team_generator(school_name, team_nickname, player_list, **kwargs):
    # Function to generate team

    # Save file name
    file_name = kwargs['file_path'] if 'file_name' in kwargs else f'{school_name}.json'

    # Generate json
    data = \
        {
            "school_name": school_name,
            "name": team_nickname,
            "players": player_list
        }

    # Write to file
    with open(file_name, 'w') as f:
        json.dump(file_name, f)

def create_player(age, name, position, city, state, gen_random=False, **kwargs):
    # Create data
    data = {}
    data['age'] = age
    data['name'] = name
    data['position'] = position
    data['city'] = city
    data['state'] = state

    # Generate attributes (everything to some value unless specified otherwise)
    if gen_random:
        # Target rating
        target_rating = kwargs['target_rating'] if 'target_rating' in kwargs else 3

        # Determine rating bounds based of star rating (target rating)
        if target_rating == 1:
            rating_bounds = (0, 50)
        elif target_rating == 2:
            rating_bounds = (50, 65)
        elif target_rating == 3:
            rating_bounds = (65, 80)
        elif target_rating == 4:
            rating_bounds = (80, 90)
        elif target_rating == 5:
            rating_bounds = (90, 99)

        # Have different distribution for 5-star players
        if target_rating != 5:
            dist = generate_uniform_dist(rating_bounds)
        else:
            dist = generate_expo_dist(rating_bounds)

        # Generate random rating from distribution (cap at 90)
        rating = dist.rvs(size=1)[0]
        if rating > 99:
            rating = 99

        # Define rating deviation
        rating_dev = 5 if rating >= 5 else 0;

        # Generate key ratings for everyone else and then recursively
        # call function so that it will fill all other values to 10
        ratings_dict = {}
        if position == 'QB':
            # TODO: Change the attributes
            [arm_power, accuracy, speed, agility, acceleration, jumping, strength] = generate_ratings(7, rating_dev=rating_dev, rating=rating)
            rating_dict = {'arm_power': arm_power, 'accuracy': accuracy, 'speed': speed, 'agility': agility, 'acceleration': acceleration, 'jumping': jumping, 'strength':strength}
        elif position == 'RB':
            [catching, run_block, pass_block, speed, agility, acceleration, jumping, strength] = generate_ratings(8, rating_dev=rating_dev, rating=rating)
            rating_dict = {'catching': catching, 'run_block': run_block, 'pass_block': pass_block, 'speed': speed, 'agility': agility, 'acceleration': acceleration, 'jumping': jumping, 'strength': strength}
        elif position == 'WR':
            [catching, run_block, pass_block, speed, agility, acceleration, jumping, strength] = generate_ratings(8, rating_dev=rating_dev, rating=rating)
            rating_dict = {'catching': catching, 'run_block': run_block, 'pass_block': pass_block, 'speed': speed, 'agility': agility, 'acceleration': acceleration, 'jumping': jumping, 'strength': strength}
        elif position == 'TE':
            [catching, run_block, pass_block, speed, agility, acceleration, jumping, strength] = generate_ratings(8, rating_dev=rating_dev, rating=rating)
            rating_dict = {'catching': catching, 'run_block': run_block, 'pass_block': pass_block, 'speed': speed, 'agility': agility, 'acceleration': acceleration, 'jumping': jumping, 'strength': strength}
        elif position == 'FB':
            [catching, run_block, pass_block, speed, agility, acceleration, jumping, strength] = generate_ratings(8, rating_dev=rating_dev, rating=rating)
            rating_dict = {'catching': catching, 'run_block': run_block, 'pass_block': pass_block, 'speed': speed, 'agility': agility, 'acceleration': acceleration, 'jumping': jumping, 'strength': strength}
        elif (position == 'T' or position == 'G' or position == 'C'):
            [run_block, pass_block, speed, agility, acceleration, jumping, strength] = generate_ratings(7, rating_dev=rating_dev, rating=rating)
            rating_dict = {'run_block': run_block, 'pass_block': pass_block, 'speed': speed, 'agility': agility, 'acceleration': acceleration, 'jumping': jumping, 'strength': strength}
        elif (position == 'DE' or position == 'DT' or position == 'NT'):
            [tackling, run_defense, pass_rush, agility, acceleration, jumping, strength] = generate_ratings(7, rating_dev=rating_dev, rating=rating)
            rating_dict = {'tackling': tackling, 'run_defense': run_defense, 'pass_rush': pass_rush, 'agility': agility, 'acceleration': acceleration, 'jumping': jumping, 'strength': strength}
        elif position == 'LB':
            [tackling, run_defense, pass_rush, agility, acceleration, jumping, strength] = generate_ratings(7, rating_dev=rating_dev, rating=rating)
            rating_dict = {'tackling': tackling, 'run_defense': run_defense, 'pass_rush': pass_rush, 'agility': agility, 'acceleration': acceleration, 'jumping': jumping, 'strength': strength}
        elif position == 'CB':
            [tackling, run_defense, pass_rush, agility, acceleration, jumping, strength] = generate_ratings(7, rating_dev=rating_dev, rating=rating)
            rating_dict = {'tackling': tackling, 'run_defense': run_defense, 'pass_rush': pass_rush, 'agility': agility, 'acceleration': acceleration, 'jumping': jumping, 'strength': strength}
        elif position == 'S':
            [tackling, run_defense, pass_rush, agility, acceleration, jumping, strength] = generate_ratings(7, rating_dev=rating_dev, rating=rating)
            rating_dict = {'tackling': tackling, 'run_defense': run_defense, 'pass_rush': pass_rush, 'agility': agility, 'acceleration': acceleration, 'jumping': jumping, 'strength': strength}
        elif position == 'ATH':
            [tackling, run_defense, pass_rush, catching, run_block, pass_block, speed, agility, acceleration, jumping, strength] = generate_ratings(11, rating_dev=15, rating=rating)
            rating_dict = {'catching': catching, 'run_block': run_block, 'pass_block': pass_block, 'speed': speed, 'tackling': tackling, 'run_defense': run_defense, 'pass_rush': pass_rush, 'agility': agility, 'acceleration': acceleration, 'jumping': jumping, 'strength': strength}
        elif position == 'P':
            [kick_power, kick_accuracy] = generate_ratings(2, rating_dev=rating_dev, rating=rating)
            rating_dict = {'kick_power': kick_power, 'kick_accuracy': kick_accuracy}
        elif position == 'K':
            [kick_power, kick_accuracy] = generate_ratings(2, rating_dev=rating_dev, rating=rating)
            rating_dict = {'kick_power': kick_power, 'kick_accuracy': kick_accuracy}

        # Generate random traits from trait pool
        trait_pool = ['LEADER', 'ECCENTRIC', 'RECKLESS',
                      'CALM', 'ANXIOUS', 'JOKESTER',
                      'COCKY', 'INTELLIGENT', 'BRAVE',
                      'OUTSPOKEN', 'HONEST', 'COWARDLY', 'AMBITIOUS',
                       'FLEXIBLE', 'FRIENDLY', 'HOSTILE']

        # Exclusivity mappings
        exclusivity_map = {'BRAVE': 'COWARDLY',
                           'FRIENDLY': 'HOSTILE',
                           'CALM': 'ANXIOUS',
                           }
        current_traits = kwargs['current_traits'] if 'current_traits' in kwargs else []

        # Iterate through trait list
        for trait in current_traits:
            # Remove trait from trait pool and it's pair if it exists in exclusivity
            trait_pool.remove(trait)
            if trait in exclusivity_map:
                trait_pool.remove(exclusivity_map[trait])


        # Determine how many traits to add
        max_traits = 6 # This is gonna be a constant; max traits any player can have is 6
        n_traits = len(current_traits)
        desired_trait_count = random.randint(0, min(6, max_traits - n_traits))

        # Loop through and add traits
        while len(current_traits) < desired_trait_count:
            trait_to_add = random.choice(trait_pool)
            current_traits.append(trait_to_add)
            trait_pool.remove(trait_to_add)
            if trait_to_add in exclusivity_map:
                try:
                    trait_pool.remove(exclusivity_map[trait_to_add])
                except:
                    continue

        rating_dict['traits'] = current_traits
        data = create_player(age, name, position, city, state, **rating_dict)
        return data
    else:
        # TODO: Might change this to be more compatible with new traits added. We want to read in values that exist in kwargs, else use default val of 50
        speed = kwarg_checker(kwargs, 'speed')
        agility = kwarg_checker(kwargs, 'agility')
        acceleration = speed = kwarg_checker(kwargs, 'acceleration')
        strength = kwarg_checker(kwargs, 'strength')
        jumping = kwarg_checker(kwargs, 'jumping')
        aggressiveness = kwarg_checker(kwargs, 'aggressiveness')
        composure = kwarg_checker(kwargs, 'composure')
        vision = kwarg_checker(kwargs, 'vision')
        arm_power = kwarg_checker(kwargs, 'arm_power')
        accuracy = kwarg_checker(kwargs, 'accuracy')
        catching = kwarg_checker(kwargs, 'catching')
        kick_power = kwarg_checker(kwargs, 'kick_power')
        kick_accuracy = kwarg_checker(kwargs, 'kick_accuracy')
        tackling = kwarg_checker(kwargs, 'tackling')
        pass_rush = kwarg_checker(kwargs, 'pass_rush')
        rush_defense = kwarg_checker(kwargs, 'rush_defense')
        pass_block = kwarg_checker(kwargs, 'pass_block')
        run_block = kwarg_checker(kwargs, 'run_block')

        ratings = {'speed': speed, 'agility': agility, 'acceleration': acceleration,
                   'strength': strength, 'jumping': jumping, 'aggressiveness': aggressiveness,
                   'composure': composure, 'vision': vision, 'arm_power': arm_power,
                   'accuracy': accuracy, 'catching': catching, 'kick_power': kick_power,
                   'kick_accuracy': kick_accuracy, 'tackling': tackling, 'pass_rush': pass_rush,
                   'rush_defense': rush_defense, 'pass_block': pass_block, 'run_block': run_block}
        traits = kwargs['traits'] if 'traits' in kwargs else []
        data['traits'] = traits
        data['attributes'] = ratings
        return data

def generate_uniform_dist(rating_bounds):
    dist = uniform(loc=rating_bounds[0], scale=rating_bounds[1] - rating_bounds[0])
    return dist

def generate_expo_dist(rating_bounds, **kwargs):
    scale = kwargs['scale'] if 'scale' in kwargs else 3.0
    dist = expon(loc=rating_bounds[0], scale=scale)
    return dist

def generate_norm_dist(rating_bounds):
    rating_bounds_mid = (rating_bounds[0] + rating_bounds[1])/2
    dist = norm(loc=rating_bounds_mid)
    return dist

def kwarg_checker(kwargs, string):
    return kwargs[string] if string in kwargs else 10

def generate_ratings(n_att, rating_dev, rating):
    rating_bounds = (rating - rating_dev, rating + rating_dev)

    # Get distribution
    dist = generate_norm_dist(rating_bounds)
    return dist.rvs(size=n_att)


def generate_cities_df():
    state_names = ["Alaska", "Alabama", "Arkansas", "Arizona", "California", "Colorado", "Connecticut", "District ", "of Columbia", "Delaware", "Florida", "Georgia", "Guam", "Hawaii", "Iowa", "Idaho", "Illinois", "Indiana", "Kansas", "Kentucky", "Louisiana", "Massachusetts", "Maryland", "Maine", "Michigan", "Minnesota", "Missouri", "Mississippi", "Montana", "North Carolina", "North Dakota", "Nebraska", "New Hampshire", "New Jersey", "New Mexico", "Nevada", "New York", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Puerto Rico", "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Virginia", "Virgin Islands", "Vermont", "Washington", "Wisconsin", "West Virginia", "Wyoming"]
    path_to_csv = '../../../data/cities.csv'
    df = pd.read_csv(path_to_csv, delimiter='|', usecols=[0,1,2])
    df = df[df['State full'].isin(state_names)]
    return df


def generate_city(state, database_df, **kwargs):
    n = kwargs['n'] if 'n' in kwargs else 1
    options = [state]
    tmp_df = database_df[database_df['State full'].isin(options)]
    if n == 1:
        try:
            return tmp_df.sample()['City']
        except:
            return None
    else:
        return list(tmp_df.sample(n)['City'])


def generate_state(n=1):
    state_names = ["Alaska", "Alabama", "Arkansas", "Arizona", "California", "Colorado", "Connecticut", "Washington, D.C.", "Delaware", "Florida", "Georgia", "Guam", "Hawaii", "Iowa", "Idaho", "Illinois", "Indiana", "Kansas", "Kentucky", "Louisiana", "Massachusetts", "Maryland", "Maine", "Michigan", "Minnesota", "Missouri", "Mississippi", "Montana", "North Carolina", "North Dakota", "Nebraska", "New Hampshire", "New Jersey", "New Mexico", "Nevada", "New York", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Puerto Rico", "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Virginia", "Virgin Islands", "Vermont", "Washington", "Wisconsin", "West Virginia", "Wyoming"]
    states = []
    if n > 1:
        for i in range(n):
            states.append(random.choice(state_names))
        return states
    else:
        return random.choice(state_names)

def generate_random_hometown(n, cities_df):
    home_towns = []
    for i in range(n):
        # Find state
        state = generate_state()

        # Find city
        city_df = generate_city(state, cities_df)
        try:
            state_short = list(cities_df['State short'].iloc[city_df.index])[0]
            city_name = list(cities_df['City'].iloc[city_df.index])[0]
        except:
            continue

        # Generate string
        ht = (city_name, state_short)
        home_towns.append(ht)
    return home_towns

def generate_player_list():
    position_list = ['QB', 'RB', 'WR', 'TE', 'FB', 'T', 'G', 'C',
                     'DE', 'DT', 'NT', 'LB', 'CB', 'S', 'ATH',
                     'P', 'K']

    n = 20

    # Generate cities
    cities_df = generate_cities_df()
    cities = generate_random_hometown(n, cities_df)

    # Loop through and create player list
    player_list = []
    for i in range(n):
        name = names.get_full_name(gender='male')
        pos = random.choice(position_list)
        age = random.randint(18,24)
        target_rating = random.randint(1,5)
        try:
            (home_city, home_state) = cities[i]
        except:
            generate_player_list()
        player = create_player(age, name, pos,
                               home_city, home_state,
                               True, target_rating=target_rating)
        player_list.append(player)

    return player_list


if __name__ == '__main__':
    pllist = generate_player_list()
    print(pllist)

