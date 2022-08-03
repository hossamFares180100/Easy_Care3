package com.example.easycare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.example.easycare.notificationService.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.StringTokenizer;

import pl.droidsonroids.gif.GifImageView;

import static android.view.Gravity.START;

public class CheckUpActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    EditText req;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    public BottomNavigationView bottomNavigationView;
    Button get;
    String token="";
    GifImageView wo;
    View view_nave;
    String query = "", query3 = "", query4 = "";
    PyObject main_program;
    Dialog dialog_q, dialog;
    Button btn_yes, btn_no, btn_ok;
    TextView questionTextView, questionName;
    boolean flagQ = false, flagN = false;
    GifImageView status;
    EditText edit_name;
    FirebaseUser user;
    FirebaseAuth mAuth;
    int imoj = R.drawable.que;
    int m = 0;
    public static String NODE_USER = "users";
    int yesNum = 0;
    int i = 0, j = 0;
    String name = "";
    Python py;
    String[] brain_and_nerve_diseases = {"difficulty walking", "difficulty speaking", "headache with cramping in the neck", "memory loss or problems with general understanding", "stare into space", "sharp movements of the hands and legs", "unconsciousness", "body stiffness", "shaking and twitching", "tongue biting", "repeat the same sentences and words", "forget conversations or dates", "misplace things", "loss of sense of time", "mood swings", "lack of trust in_others", "depression", "fear", "numbness lack of feeling and feeling or weakness in the limbs", "double or blurry vision", "pain and itching in different parts of the body", "feeling of what looks like an electric blow when moving the head certain movements", "tremor loss of coordination between organs of the body or loss of balance while walking", "fatigue", "dizziness", "burning or electric burning pain", "pain that feels like cutting or stabbing", "hyperalgesia", "sensation of tingling or numbness and stinging in the affected organ", "loss of sensation", "gradual decrease in hearing on the side where_the tumor developed", "vestibular disturbance and wheezing in the ear", "headache nausea vomiting and even drowsiness", "disturbances in swallowing disturbances insensation in the face", "temporary loss of consciousness", "bradycardia", "stinging or burning pain in the feet", "excessive sensitivity to touch in the painful area or a feeling of numbness", "phantom pain that occurs after an organ has been cut", "involuntary twisting movements", "severe reactions madness", "disturbances of speech swallowing walking and eyesight", "depression", "disturbances in the childs motor abilities and performance", "sensory disturbances in the senses such as deafness and blindness", "impaired intelligence and disturbances in the functioning of organs", "tense muscles", "risk of joint dislocation", "bone deformation", "continuous involuntary movement in the limbs", "lack of balance and lack of control over movements", "headache", "weakness and emaciation", "motion blur", "difficulty walking", "seizures of convulsions", "change in perceptual mental state", "feeling sick and vomiting", "changes in vision Sight", "difficulty speaking", "gradual changes in mental ability or emotional perception"}, arrName = {"Hi. How are you? First of all tell me your name Please : "};
    String[] brain_and_nerve_diseases2 = {"difficulty_walking", "difficulty_speaking", "headache_with_cramping_in_the_neck", "memory_loss_or_problems_with_general_understanding", "stare_into_space", "sharp_movements_of_the_hands_and_legs", "unconsciousness", "body_stiffness", "shaking_and_twitching", "tongue_biting", "repeat_the_same_sentences_and_words", "forget_conversations_or_dates", "misplace_things", "loss_of_sense_of_time", "mood_swings", "lack_of_trust_in_others", "depression", "fear", "numbness_lack_of_feeling_and_feelin_or_weakness_in_the_limbs", "double_or_blurry_vision", "pain_and_itching_in_different_parts_of_the_body", "feeling_of_what_looks_like_an_electric_blow_when_moving_the_head_certain_movements", "tremor_loss_of_coordination_between_organs_of_the_body_or_loss_of_balance_while_walking", "fatigue", "dizziness", "burning_or_electric_burning_pain", "pain_that_feels_like_cutting_or_stabbing", "hyperalgesia", "sensation_of_tingling_or_numbness_and_stinging_in_the_affected_organ", "loss_of_sensation", "gradual_decrease_in_hearing_on_the_side_where_the_tumor_developed", "vestibular_disturbance_and_wheezing_in_the_ear", "headache_nausea_vomiting_and_even_drowsiness", "disturbances_in_swallowing_disturbances_insensation_in_the_face", "temporary_loss_of_consciousness", "bradycardia", "stinging_or_burning_pain_in_the_feet", "excessive_sensitivity_to_touch_in_the_painful_area_or_a_feeling_of_numbness", "phantom_pain_that_occurs_after_an_organ_has_been_cut", "involuntary_twisting_movements", "severe_reactions_madness", "disturbances_of_speech_swallowing_walking_and_eyesight", "depression", "disturbances_in_the_childs_motor_abilities_and_performance", "sensory_disturbances_in_the_senses_such_as_deafness_and_blindness", "impaired_intelligence_and_disturbances_in_the_functioning_of_organs", "tense_muscles", "risk_of_joint_dislocation", "bone_deformation", "continuous_involuntary_movement_in_the_limbs", "lack_of_balance_and_lack_of_control_over_movements", "headache", "weakness_and_emaciation", "motion_blur", "difficulty_walking", "seizures_of_convulsions", "change_in_perceptual_mental_state", "feeling_sick_and_vomiting", "changes_in_vision_Sight", "difficulty_speaking", "gradual_changes_in_mental_ability_or_emotional_perception"};

    String[] women_disease = {"poor mood", "indifference and lack of enjoyment", "disturbances in appetite and sleep", "lack of self esteem", "feelings of guilt", "weight gain", "hair on face and body", "reduce hair density on the head", "depression", "pressure", "twisting the tumor", "get bleeding after the end of their menstrual cycle", "pelvic tumor", "an accelerated growth of muscle tissue in the womb", "abdominal pain", "random lump", "bleeding from the vagina", "blood stained vaginal secretions", "feeling aches when having sexual relations", "abdominal pain or swelling", "difficulty urinating", "or passing urine often and often", "dull pain in the lower back", "pain when having sexual relations", "itching and irritation of the vagina and vulva", "a burning sensation", "especially during sexual intercourse or during urination", "redness and swelling of the vulva", "pain and inflammation of the vagina", "vaginal rash", "back pain", "weight loss with curvature", "fractures in the vertebrae", "fractures in the palms of the hands", "fractures in the pelvis of the thighs", "irregularity in the menstrual cycle", "decreased fertility", "vaginal dryness", "heat flashes", "insomnia"};
    String[] women_disease2 = {"poor_mood", "indifference_and_lack_of_enjoyment", "disturbances_in_appetite_and_sleep", "lack_of_self_esteem", "feelings_of_guilt", "weight_gain", "hair_on_face_and_body", "reduce_hair_density_on_the_head", "depression", "pressure", "twisting_the_tumor", "get_bleeding_after_the_end_of_their_menstrual_cycle", "pelvic_tumor", "an_accelerated_growth_of_muscle_tissue_in_the_womb", "abdominal_pain", "random_lump", "bleeding_from_the_vagina", "blood-stained_vaginal_secretions", "feeling_aches_when_having_sexual_relations", "abdominal_pain_or_swelling", "difficulty_urinating", "or_passing_urine_often_and_often", "dull_pain_in_the_lower_back", "pain_when_having_sexual_relations", "itching_and_irritation_of_the_vagina_and_vulva", "a_burning_sensation", "especially_during_sexual_intercourse_or_during_urination", "redness_and_swelling_of_the_vulva", "pain_and_inflammation_of_the_vagina", "vaginal_rash", "back_pain", "weight_loss_with_curvature", "fractures_in_the_vertebrae", "fractures_in_the_palms_of_the_hands", "fractures_in_the_pelvis_of_the_thighs", "irregularity_in_the_menstrual_cycle", "decreased_fertility", "vaginal_dryness", "heat_flashes", "insomnia"};

    String[] bone_diseases = {"symp local sensitivity", "limb weakness", "sense disturbances", "disturbances in bowel control", "seizures", "ulcers within the nasopharyngeal cavity", "redness and elevation of the skin", "sensitivity", "pain following joint overstimulation", "swollen osteoclasts", "joint swelling", "fluid buildup in the joints", "pain in the lower back", "difficulty moving", "ankylosing spondylitis", "swelling and inflammation in the joints", "tired", "fever", "chest pain", "hair loss", "ache", "stiffness", "swelling", "redness", "muscle tension", "problems with building back", "sciatica", "arthritis"};
    String[] bone_diseases2 = {"symp_local_sensitivity", "limb_weakness", "sense_disturbances", "disturbances_in_bowel_control", "seizures", "ulcers_within_the_nasopharyngeal_cavity", "redness_and_elevation_of_the_skin", "sensitivity", "pain_following_joint_overstimulation", "swollen_osteoclasts", "joint_swelling", "fluid_buildup_in_the_joints", "pain_in_the_lower_back", "difficulty_moving", "ankylosing_spondylitis", "swelling_and_inflammation_in_the_joints", "tired", "fever", "chest_pain", "hair_loss", "ache", "stiffness", "swelling", "redness", "muscle_tension", "problems_with_building_back", "sciatica", "arthritis"};

    String[] children_diseases = {"feeling of a throbbing mass in the abdomen", "abdominal pain and back pain", "complaints relate to aneurysm pressure", "angina", "feels hemodynamic changes", "hypotension", "hemodynamic", "heart rate", "feeling that the heart is beating too quickly", "dizziness", "fainting", "hard breathing", "mild aches in the head", "dizziness", "nosebleeds more than usual", "pressure", "feeling of congestion", "dizziness", "sweating", "rapid fatigue upon exertion", "anxiety", "fainting", "pain in chest", "feeling of pressure in the chest", "shortness of breath", "palpitations", "the feeling of a pounding heart in the chest", "sensation of head turning", "weakness", "shortness of breath", "fatiguem", "swelling in different parts of the body", "pain in the rib cage", "pain increases when a deep inhale is taken", "pain increases when eating or when moving"};
    String[] children_diseases2 = {"feeling_of_a_throbbing_mass_in_the_abdomen", "abdominal_pain_and_back_pain", "complaints_relate_to_aneurysm_pressure", "angina", "feels_hemodynamic_changes", "hypotension", "hemodynamic", "heart_rate", "feeling_that_the_heart_is_beating_too_quickly", "dizziness", "fainting", "hard_breathing", "mild_aches_in_the_head", "dizziness", "nosebleeds_more_than_usual", "pressure", "feeling_of_congestion", "dizziness", "sweating", "rapid_fatigue_upon_exertion", "anxiety", "fainting", "pain_in_chest", "feeling_of_pressure_in_the_chest", "shortness_of_breath", "palpitations", "the_feeling_of_a_pounding_heart_in_the_chest", "sensation_of_head_turning", "weakness", "shortness_of_breath", "fatiguem", "swelling_in_different_parts_of_the_body", "pain_in_the_rib_cage", "pain_increases_when_a_deep_inhale_is_taken", "pain_increases_when_eating_or_when_moving"};

    String[] dental_diseases = {"localized swelling with or without a wound", "an abnormal white spot of the color of the surrounding mucous membrane", "an irregular red spot from the surrounding mucous membrane", "red and white mixed spot", "a brown to black spot which may be associated with local swelling or local lesion but not always", "the appearance of vesicles on the lips membrane in the border between the mucous membrane of the lips and the skin", "the emergence of initial signs and symptoms after 24 hours", "feeling discomfort", "pain and stinging in the area of injury", "gums bleed when brushing teeth", "redness and swelling and sensitivity of the gums", "retraction of the gums", "loss of teeth or tooth movement", "toothache", "tooth sensitivity", "dental holes that can be seen by eye", "pain when biting food", "the appearance of pus around the tooth"};
    String[] dental_diseases2 = {"localized_swelling_with_or_without_a_wound", "an_abnormal_white_spot_of_the_color_of_the_surrounding_mucous_membrane", "an_irregular_red_spot_from_the_surrounding_mucous_membrane", "red_and_white_mixed_spot", "a_brown_to_black_spot_which_may_be_associated_with_local_swelling_or_local_lesion_but_not_always", "the_appearance_of_vesicles_on_the_lips_membrane_in_the_border_between_the_mucous_membrane_of_the_lips_and_the_skin", "the_emergence_of_initial_signs_and_symptoms_after_24_hours", "feeling_discomfort", "pain_and_stinging_in_the_area_of_injury", "gums_bleed_when_brushing_teeth", "redness_and_swelling_and_sensitivity_of_the_gums", "retraction_of_the_gums", "loss_of_teeth_or_tooth_movement", "toothache", "tooth_sensitivity", "dental_holes_that_can_be_seen_by_eye", "pain_when_biting_food", "the_appearance_of_pus_around_the_tooth"};

    String[] dermatoses_diseases = {"atigue", "headaches", "fever", "swollen", "painful joints", "fever", "sore_throat", "red", "watery_eyes", "loss_of_appetite", "cough", "hair loss", "affected areas is greasy", "affected areas is itchy", "affected areas is red", "cough", "runny_nose", "sneezing", "itchy", "watery eyes", "facial flushing raised red bumps", "facial redness", "skin dryness", "itchy raised welts", "red warm and mildly painful to the touch", "can be small round and ring shaped", "affected area will often tingle or burn before the sore is visible", "outbreaks may also be accompanied by mild"};
    String[] dermatoses_diseases2 = {"atigue", "headaches", "fever", "swollen", "painful_joints", "fever", "sore_throat", "red", "watery_eyes", "loss_of_appetite", "cough", "hair_loss", "affected_areas_is_greasy", "affected_areas_is_itchy", "affected_areas_is_red", "cough", "runny_nose", "sneezing", "itchy", "watery_eyes", "facial_flushing_raised_red_bumps", "facial_redness", "skin_dryness", "itchy_raised_welts", "red_warm_and_mildly_painful_to_the_touch", "can_be_small_round_and_ring_shaped", "affected_area_will_often_tingle_or_burn_before_the_sore_is_visible", "outbreaks_may_also_be_accompanied_by_mild"};

    String[] eyes_illnesses = {"blurry blurred or dim vision", "increased difficulty seeing at night", "sensitivity to light and dazzle", "halos around light sources", "the need for brighter light to read or do other work", "change eyeglasses or contact lenses frequently and frequently", "faded or yellowish colors", "double vision in one eye", "retinal dystrophy pigmentation in the form of bone particles characteristic of the disease", "difficulty seeing distant objects", "difficulty seeing a board or TV or movie screen", "decline in education in sporting activity or at work"};
    String[] eyes_illnesses2 = {"blurry_blurred_or_dim_vision", "increased_difficulty_seeing_at_night", "sensitivity_to_light_and_dazzle", "halos_around_light_sources", "the_need_for_brighter_light_to_read_or_do_other_work", "change_eyeglasses_or_contact_lenses_frequently_and_frequently", "faded_or_yellowish_colors", "double_vision_in_one_eye", "retinal_dystrophy_pigmentation_in_the_form_of_bone_particles_characteristic_of_the_disease", "difficulty_seeing_distant_objects", "difficulty_seeing_a_board_or_TV_or_movie_screen", "decline_in_education_in_sporting_activity_or_at_work"};

    String[] ear_nose_and_throat = {"sneezing", "cough", "watery eyes", "mild headache or body aches", "runny nose", "low body temperature", "sneeze", "cough", "itching", "runny nose", "stuffy nose", "swelling under the eye", "headache", "sore throat", "watery", "red or swollen eyes", "sinusitis", "middle ear canal infections", "difficulty_hearing_others_clearly", "slowly_repeat_the_request_to_speak", "listening_to_music", "watching_tV_at_a_higher_volume", "difficulty_hearing_the_voice_of_the_speaker_on_the_phone", "withdrawing_from_conversations", "feelingtired or stressed to focus while listening", "as for the symptoms of hearing loss in one ear", "hearing is worse when the sound comes from one side", "all sounds are generally quieter than usual", "difficulty knowing the source of the sound", "difficulty ignoring background noise", "distinguishing between different sounds", "difficulty understanding speech", "difficulty hearing in noisy places", "ear pain especially when lyin down", "difficulty falling asleep", "crying more than usual and pulling out the ear", "failure to respond to sounds", "loss of balance High temperature38 C or higher", "discharge from the ear Anorexia", "pain in the ear", "difficulty falling asleep", "discharge from the ear hearing impairment"};
    String[] ear_nose_and_throat2 = {"sneezing", "cough", "watery_eyes", "mild_headache_or_body_aches", "runny_nose", "low_body_temperature", "sneeze", "cough", "itching", "runny_nose", "stuffy_nose", "swelling_under_the_eye", "headache", "sore_throat", "watery", "red_or_swollen_eyes", "sinusitis", "middle_ear_canal_infections", "difficulty_hearing_others_clearly", "slowly_repeat_the_request_to_speak", "listening_to_music", "watching_tV_at_a_higher_volume", "difficulty_hearing_the_voice_of_the_speaker_on_the_phone", "withdrawing_from_conversations", "feelingtired_or_stressed_to_focus_while_listening", "as_for_the_symptoms_of_hearing_loss_in_one_ear", "hearing_is_worse_when_the_sound_comes_from_one_side", "all_sounds_are_generally_quieter_than_usual", "difficulty_knowing_the_source_of_the_sound", "difficulty_ignoring_background_noise", "distinguishing_between_different_sounds", "difficulty_understanding_speech", "difficulty_hearing_in_noisy_places", "ear_pain_especially_when_lyin_down", "difficulty_falling_asleep", "crying_more_than_usual_and_pulling_out_the_ear", "failure_to_respond_to_sounds", "loss_of_balance_high_temperature38_C_or_higher", "discharge_from_the_ear_Anorexia", "pain_in_the_ear", "difficulty_falling_asleep", "discharge_from_the_ear_hearing_impairment"};

    ArrayList<String> yesQue;


    private void setId() {
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        bottomNavigationView = findViewById(R.id.navigation_bottom);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_up);
        wo=findViewById(R.id.gif_img);
        setId();
        setDrawerLayout();
        navigationView.setNavigationItemSelectedListener(this);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        mAuth = FirebaseAuth.getInstance();
        //drawerLayout.closeDrawer(GravityCompat.START);
        //navigationView.setNavigationItemSelectedListener(this);
        // view_nave=navigationView.getHeaderView(0);
        //  bottomNavigationView.setSelectedItemId(R.id.navigationMenu);


        FirebaseMessaging.getInstance().subscribeToTopic("updates").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                }else{
                    Toast.makeText(CheckUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {

                        if(task.isSuccessful()){
                            token = task.getResult().getToken();
                        }else{

                        }

                    }
                });




        yesQue = new ArrayList<>();
        yesNum = 0;

        if (!Python.isStarted())
            Python.start(new AndroidPlatform(this));
        py = Python.getInstance();

        showDialog();

    }


    private void showDialogQuestion() {

        if (i == brain_and_nerve_diseases.length - 1) {
            saveDiseases(query);
            showAnswer("Sorry, I don't seem to be able to diagnose the disease.");
        }
        else if(i == eyes_illnesses.length - 1) {
            saveDiseases(query);
            showAnswer("Sorry, I don't seem to be able to diagnose the disease.");
        }else if(i == ear_nose_and_throat.length - 1) {
            saveDiseases(query);
            showAnswer("Sorry, I don't seem to be able to diagnose the disease.");
        }else if(i == bone_diseases.length - 1) {
            saveDiseases(query);
            showAnswer("Sorry, I don't seem to be able to diagnose the disease.");
        }else if(i == children_diseases.length - 1) {
            saveDiseases(query);
            showAnswer("Sorry, I don't seem to be able to diagnose the disease.");
        }else if(i == women_disease.length - 1) {
            saveDiseases(query);
            showAnswer("Sorry, I don't seem to be able to diagnose the disease.");
        }else if(i == dental_diseases.length - 1) {
            saveDiseases(query);
            showAnswer("Sorry, I don't seem to be able to diagnose the disease.");
        }else if(i == dermatoses_diseases.length - 1) {
            saveDiseases(query);
            showAnswer("Sorry, I don't seem to be able to diagnose the disease.");
        }
        else {
            dialog = new Dialog(CheckUpActivity.this);
            dialog.setContentView(R.layout.question_layout);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            Window window = dialog.getWindow();
            window.setGravity(Gravity.CENTER);
            window.getAttributes().windowAnimations = R.style.DialogAnimation;
            btn_yes = dialog.findViewById(R.id.btn_yes);
            btn_no = dialog.findViewById(R.id.btn_no);
            questionTextView = dialog.findViewById(R.id.txt_que);
            if (query.charAt(0) == 'd' && query.charAt(1) == 'e' && query.charAt(2) == 'r') {
                questionTextView.setText("have " + dermatoses_diseases[i] + " (y/n) ?");
            } else if (query.charAt(0) == 'b' && query.charAt(1) == 'r') {
                questionTextView.setText("have " + brain_and_nerve_diseases[i] + " (y/n) ?");
            } else if (query.charAt(0) == 'w') {
                questionTextView.setText("have " + women_disease[i] + " (y/n) ?");
            } else if (query.charAt(0) == 'e' && query.charAt(1) == 'y') {
                questionTextView.setText("have " + eyes_illnesses[i] + " (y/n) ?");
            } else if (query.charAt(0) == 'b' && query.charAt(1) == 'o') {
                questionTextView.setText("have " + bone_diseases[i] + " (y/n) ?");
            } else if (query.charAt(0) == 'c') {
                questionTextView.setText("have " + children_diseases[i] + " (y/n) ?");
            } else if (query.charAt(0) == 'd' && query.charAt(1) == 'e' && query.charAt(2) == 'n') {
                questionTextView.setText("have " + dental_diseases[i] + " (y/n) ?");
            } else if (query.charAt(0) == 'e' && query.charAt(1) == 'a' && query.charAt(2) == 'r') {
                questionTextView.setText("have " + ear_nose_and_throat[i] + " (y/n) ?");
            }
            status = dialog.findViewById(R.id.status_qu);
            status.setImageResource(imoj);
            window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
            dialog.show();
            btn_yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    yesNum++;
                    imoj = R.drawable.que;
                    if (query.charAt(query.length() - 1) != '[')
                        query += ',';


                    if (query.charAt(0) == 'd' && query.charAt(1) == 'e' && query.charAt(2) == 'r') {
                        query += dermatoses_diseases2[i];
                        yesQue.add(dermatoses_diseases2[i]);
                    } else if (query.charAt(0) == 'b' && query.charAt(1) == 'r') {
                        query += brain_and_nerve_diseases2[i];
                        yesQue.add(brain_and_nerve_diseases2[i]);
                    } else if (query.charAt(0) == 'w') {
                        query += women_disease2[i];
                        yesQue.add(women_disease2[i]);
                    } else if (query.charAt(0) == 'e' && query.charAt(1) == 'y') {
                        query += eyes_illnesses2[i];
                        yesQue.add(eyes_illnesses2[i]);
                    } else if (query.charAt(0) == 'b' && query.charAt(1) == 'o') {
                        query += bone_diseases2[i];
                        yesQue.add(bone_diseases2[i]);
                    } else if (query.charAt(0) == 'c') {
                        query += children_diseases2[i];
                        yesQue.add(children_diseases2[i]);
                    } else if (query.charAt(0) == 'd' && query.charAt(1) == 'e' && query.charAt(2) == 'n') {
                        query += dental_diseases2[i];
                        yesQue.add(dental_diseases2[i]);
                    } else if (query.charAt(0) == 'e' && query.charAt(1) == 'a' && query.charAt(2) == 'r') {
                        query += ear_nose_and_throat2[i];
                        yesQue.add(ear_nose_and_throat2[i]);
                    }


                    flagQ = true;
                    while (flagQ) {
                        i++;
                        flagQ = false;
                        if (i < 2) {

                            dialog.dismiss();
                            showDialogQuestion();
                        } else if (i >= 2) {
                            String query2 = query;
                            query2 += "],Z,Q,B,P)";
                            dialog.dismiss();


                            Log.e("prolog : ", query2);

                            PyObject main_program = py.getModule("prolog");
                            PyObject str = main_program.callAttr("pro", query2);
                            Log.e("prolog : ", str.toString());


                            if (!str.toString().equals("['No']")) {
                                wo.setImageResource(R.drawable.woman_doctor);
                                final PyObject finalStr = str;
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        wo.setImageResource(R.drawable.woman_doctor2);
                                        showAnswer(finalStr.toString());

                                    }
                                },4000);

                            }


                            else {
                                for (int m = 0; m < yesQue.size() - 1; m++) {
                                    query4 = query3;
                                    for (int n = m + 1; n < yesQue.size(); n++) {
                                        query4 += yesQue.get(n);
                                        if (n != yesQue.size() - 1)
                                            query4 += ",";
                                    }
                                    query4 += "],Z,Q,B,P)";
                                    str = main_program.callAttr("pro", query4);

                                    Log.e("prologx : ", str.toString());
                                    Log.e("prologx : ", query4);
                                    if (!str.toString().equals("['No']"))
                                        break;

                                }


                                if (str.toString().equals("['No']"))
                                    showDialogQuestion();
                                else{
                                    wo.setImageResource(R.drawable.woman_doctor);
                                    final PyObject finalStr = str;
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            wo.setImageResource(R.drawable.woman_doctor2);
                                            showAnswer(finalStr.toString());

                                        }
                                    },4000);

                                }
                            }

                        }
                    }

                }
            });

            btn_no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    i++;

                    flagQ = true;
                    dialog.dismiss();
                    showDialogQuestion();
                }
            });

        }
    }


    private void showDialog() {
        dialog_q = new Dialog(CheckUpActivity.this);

        dialog_q.setContentView(R.layout.welcome_layout);
        dialog_q.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialog_q.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations = R.style.DialogAnimation;
        btn_ok = dialog_q.findViewById(R.id.btn_ok);
        edit_name = dialog_q.findViewById(R.id.edit_answer);
        questionName = dialog_q.findViewById(R.id.txt_que_name);
        questionName.setText(arrName[j]);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        dialog_q.show();
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flagN = true;
                name += edit_name.getText().toString().trim();
                if (name.equals(""))
                    Toast.makeText(CheckUpActivity.this, "name can't be empty..!", Toast.LENGTH_SHORT).show();
                else {
                    dialog_q.dismiss();
                    j++;
                    showAnswer("Kindly Choose The Specialization You Want To Check Up In From The List in The Left :) ");

                }
            }
        });


    }


    private void showAnswer(String ans) {

        if (!ans.equals("") && ans.charAt(0) != 'K'&&ans.charAt(0)!='S') {
            String disease = "";
            String cure = "1 - ";
            int k = 2;
            int f = 0;
            for (int i = 0; i < ans.length(); i++) {
                if (ans.charAt(i) == '[' || ans.charAt(i) == '{' || ans.charAt(i) == ':' || ans.charAt(i) == ' ' || ans.charAt(i) == 'X' || ans.charAt(i) == '\"' || ans.charAt(i) == '\'')
                    continue;
                else if (ans.charAt(i) == ',') {
                    f = i;
                    break;
                } else disease += ans.charAt(i);
            }

            for (int j = f + 1; j < ans.length(); j++) {
                if (ans.charAt(j) == '[' || ans.charAt(j) == '{' || ans.charAt(j) == ':' || ans.charAt(j) == ' ' || ans.charAt(j) == 'X' || ans.charAt(j) == '\"' || ans.charAt(j) == '\'' || ans.charAt(j) == 'P' || ans.charAt(j) == 'B' || ans.charAt(j) == 'Q' || ans.charAt(j) == 'Z' || ans.charAt(j) == ']' || ans.charAt(j) == '}')
                    continue;
                else if (ans.charAt(j) == '_')
                    cure += " ";
                else if (ans.charAt(j) == ',')
                    cure += "\n\n" + k++ + " - ";
                else cure += ans.charAt(j);
            }

            dialog_q = new Dialog(CheckUpActivity.this);
            dialog_q.setContentView(R.layout.answer_dialog);
            dialog_q.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            Window window = dialog_q.getWindow();
            window.setGravity(Gravity.CENTER);
            window.getAttributes().windowAnimations = R.style.DialogAnimation;
            btn_ok = dialog_q.findViewById(R.id.btn_ok2);
            TextView answer = dialog_q.findViewById(R.id.txt_ans);
            TextView treat = dialog_q.findViewById(R.id.txt_ans2);
            treat.setText("You probably need to :\n\n" + cure);
            answer.setText(name + ", you  probably have : " + disease);
            window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        } else {
            dialog_q = new Dialog(CheckUpActivity.this);
            dialog_q.setContentView(R.layout.answer_dialog);
            dialog_q.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            Window window = dialog_q.getWindow();
            window.setGravity(Gravity.CENTER);
            window.getAttributes().windowAnimations = R.style.DialogAnimation;
            btn_ok = dialog_q.findViewById(R.id.btn_ok2);
            TextView answer = dialog_q.findViewById(R.id.txt_ans);
            TextView treat = dialog_q.findViewById(R.id.txt_ans2);
            answer.setText(ans);
            treat.setText("");
            window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        }
        dialog_q.show();
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_q.dismiss();
                drawerLayout.openDrawer(GravityCompat.START);

            }
        });


    }


    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.nav_feedback:
                    Intent intent = new Intent(CheckUpActivity.this, FeedBackActivity.class);
                    startActivity(intent);
                    break;

                case R.id.nav_contact_us:
                    AlertDialog.Builder builder = new AlertDialog.Builder(CheckUpActivity.this);
                    View view = getLayoutInflater().inflate(R.layout.contact_us_layout, null);
                    MaterialRippleLayout face = view.findViewById(R.id.facebook);
                    MaterialRippleLayout gog = view.findViewById(R.id.tele);
                    builder.setView(view).setTitle("Contact").setIcon(R.drawable.ic_phone_dialog);
                    final AlertDialog dialog = builder.create();
                    dialog.show();
                    face.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/profile.php?id=100012746865775"));
                            startActivity(browse);
                            dialog.dismiss();

                        }
                    });

                    gog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + "hodashimo708@gmail.com"));
                            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Contact Owner");
                            startActivity(emailIntent);
                            dialog.dismiss();
                        }
                    });


                    break;


                case R.id.navigationMenu:
                    drawerLayout.openDrawer(GravityCompat.START);
                    break;
            }
            return true;
        }
    };


    private void setDrawerLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        query = "";
        query3 = "";
        yesQue.clear();
        i = 0;
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_brain:
                Toast.makeText(this, "here", Toast.LENGTH_SHORT).show();
                query += "brain_and_nerve_diseases(X,[";
                query3 += "brain_and_nerve_diseases(X,[";

                break;
            case R.id.nav_women:
                query += "women_disease(X,[";
                query3 += "women_disease(X,[";


                break;
            case R.id.nav_bones:
                query += "bone_diseases(X,[";
                query3 += "bone_diseases(X,[";

                break;
            case R.id.nav_children:
                query += "children_diseases(X,[";
                query3 += "children_diseases(X,[";


                break;
            case R.id.nav_dental:
                query += "dental_diseases(X,[";
                query3 += "dental_diseases(X,[";


                break;
            case R.id.nav_dematoses:
                query += "dermatoses_diseases(X,[";
                query3 += "dermatoses_diseases(X,[";


                break;
            case R.id.nav_eye:
                query += "eyes_illnesses(X,[";
                query3 += "eyes_illnesses(X,[";


                break;
            case R.id.nav_ear:
                query += "ear_nose_and_throat(X,[";
                query3 += "ear_nose_and_throat(X,[";


                break;


        }

        drawerLayout.closeDrawer(GravityCompat.START);
        showDialogQuestion();
        return true;
    }


    private void saveDiseases(String dis) {

        String email = mAuth.getCurrentUser().getEmail();
        User user = new User(dis, token);
        DatabaseReference dpRef = FirebaseDatabase.getInstance().getReference(NODE_USER);
        dpRef.child(mAuth.getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                } else {
                    Toast.makeText(CheckUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}