using FirebaseAdmin;
using FirebaseAdmin.Messaging;
using Google.Apis.Auth.OAuth2;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using static Google.Apis.Requests.BatchRequest;

namespace ServidorFirebase
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
            //inicializar firebase
            FirebaseApp.Create(new AppOptions()
            {
                Credential = GoogleCredential.GetApplicationDefault(),
                ProjectId = "myfirebase-577b0",
            });
        }

        private async void button1_Click(object sender, EventArgs e)
        {
            // This registration token comes from the client FCM SDKs.
            var registrationToken = new List<String>() { "cjWXagBeR06-Ba72YdjoET:APA91bGguMUFnJBq5Kqm7I4QanUdINaipOmCxeUEq3BwyJENnMgHYKsiKSOrvZo86sEfwq7Q2W4FAJBjwxnn2u7S1VP9fUrDPpfx9ieJ74fEDSIVbKHVTKE0ISjXZJoxvGS-X1nGypAN" };
            // Crear mensaje
            var message = new FirebaseAdmin.Messaging.MulticastMessage()
            {
                Tokens = registrationToken,
                Data = new Dictionary<string, string>()
                {
                    { "mensaje", mensaje.Text }
                },
                Notification = new Notification() {Body=mensaje.Text},
                Android = new AndroidConfig()
                {
                    Notification = new AndroidNotification()
                    {
                        Icon = "stock_ticker_update",
                        Color = "#ffbb86",
                    },
                },
            };
            // Send a message to the device corresponding to the provided
            // registration token.
            var response = await FirebaseMessaging.DefaultInstance.SendMulticastAsync(message);
            // Response is a message ID string.
            if (response.SuccessCount > 0)
            {
                label1.Text = "Mensaje enviado";
                btn.Enabled = false;
                mensaje.Enabled = false;
                btnReset.Enabled = true;
            }
        }

        private void textBox2_TextChanged(object sender, EventArgs e)
        {
            if (mensaje.Text.Length > 0) { 
                btn.Enabled = true;
            }
            else
            {
                btn.Enabled = false;
            }
        }

        private void btnReset_Click(object sender, EventArgs e)
        {
            btn.Enabled = true;
            mensaje.Text= string.Empty;
            mensaje.Enabled = true;
            btnReset.Enabled= false;
            label1.Text = "Sin enviar";
        }
    }
}
